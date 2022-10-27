package co.uk.redpixel.validator

import cats.effect._
import co.uk.redpixel.validator.data.FieldRules
import fs2.io.file.{Files, Path}
import io.circe.fs2.{decoder, stringArrayParser}
import fs2.text

// import com.comcast.ip4s._
// import org.http4s.ember.server._

object Main extends IOApp.Simple {

  def run: IO[Unit] = {

    def through(directory: String) =
      Path(getClass.getResource(s"/$directory").getPath)

    def getName(path: Path) =
      fs2.Stream
        .emit(path.baseName)
        .covary[IO]

    def parse(file: Path) =
      Files[IO]
        .readAll(file)
        .through(text.utf8.decode)
        .through(text.lines)
        .through(stringArrayParser)
        .through(decoder[IO, FieldRules])

    Files[IO].walk(
        through(directory = "templates")
      )
      .tail
      .map { file =>
        getName(file) zip parse(file)
      }
      .parJoinUnbounded
      .compile
      .to(Map) >> IO.unit
  }

  implicit class PathOps(private val underlying: Path) extends AnyVal {
    def baseName: String =
      underlying.fileName.toString
        .replace(underlying.extName, "")
  }

  //  EmberServerBuilder
  //    .default[IO]
  //    .withPort(port"9000")
  //    .withHost(host"localhost")
  //    .build
  //    .useForever
}
