package co.uk.redpixel.validator

import cats.effect._
//import co.uk.redpixel.validator.rules.ValidationRules.FieldRules
import fs2.io.file.{Files, Path}
import io.circe.fs2.stringArrayParser//{decoder, stringArrayParser}
//import io.circe.generic.codec.DerivedAsObjectCodec.deriveCodec

import fs2.text

//import com.comcast.ip4s._
//import org.http4s.ember.server._

object Main extends IOApp.Simple {

  def run: IO[Unit] = {

    Files[IO].walk(Path(getClass.getResource("/templates").getPath))
      .tail
      .map(
        Files[IO]
          .readAll(_)
          .through(text.utf8.decode)
          .through(text.lines)
          .through(stringArrayParser)
//          .through(decoder[IO, FieldRules])
      )
      .parJoinUnbounded
      .evalTap { x =>
        IO.delay(println(x.toString()))
      }
      .compile
      .drain

    //    new File(getClass.getResource("/templates").getPath)
    //
    //    Resource
    //      .fromAutoCloseable(IO.delay(Source.fromURL()))
    //      .use { source =>
    //        IO.delay(println(source.getLines()))
    //      }

    //    val a = Files[IO].readAll(fs2.io.file.Path("templates"))
    //      .through(text.utf8.decode)
    //      .through(text.lines)
    //      .compile.string

    //    Using.resource(getClass.getResourceAsStream("templates")) { x =>
    //      Files.
    //    }

  }

  //    EmberServerBuilder
  //      .default[IO]
  //      .withPort(port"9000")
  //      .withHost(host"localhost")
  //      .build
  //      .useForever
}
