package co.uk.redpixel.validator

import cats.effect._
import co.uk.redpixel.validator.data.Address._
import co.uk.redpixel.validator.data.{Address, FieldRules}
import fs2.text
import io.circe.fs2.{decoder, stringArrayParser}
import io.github.classgraph.{ClassGraph, Resource => ClassgraphResource}
import shapeless.LabelledGeneric
//import shapeless.HNil

import scala.jdk.CollectionConverters._

// import com.comcast.ip4s._
// import org.http4s.ember.server._

object Main extends IOApp.Simple {

  def run: IO[Unit] = {

    val _ = fs2.Stream.resource(
      Resource.fromAutoCloseable(
        IO.delay(
          new ClassGraph()
            .acceptPathsNonRecursive("/templates")
            .scan()
        )
      )
    ).flatMap { scanResult =>
      fs2.Stream.emits(
        scanResult
          .getResourcesWithExtension("json")
          .asScala
          .toVector
      )
    }.map { resource =>
      fs2.Stream.emit(resource.baseName) zip
        fs2.io.readInputStream(
          IO.delay(resource.open()),
          chunkSize = 64 * 1024
        )
        .through(text.utf8.decode)
        .through(stringArrayParser)
        .through(decoder[IO, FieldRules])
    }
    .parJoinUnbounded
    .compile
    .to(Map)

//    import shapeless.syntax.std.product._
    import shapeless.record._
//    import shapeless.syntax.singleton._

    val address = Address(
      line1 = Some(AddressLine1("James")),
      None,
      None,
      None,
      None,
      None,
      None,
      None
    )

    val gen = LabelledGeneric[Address]

    val labeled = gen.to(address)

    val a = labeled(Symbol("line1"))

    IO.println(a)

  }

  def ok(opt: Option[_]): Boolean = opt.isDefined


  implicit class ResourceOps(private val underlying: ClassgraphResource) extends AnyVal {
    def baseName: String =
      underlying.getPath
        .split("\\W+")
        .takeRight(2)
        .head
  }

  //  EmberServerBuilder
  //    .default[IO]
  //    .withPort(port"9000")
  //    .withHost(host"localhost")
  //    .build
  //    .useForever
}
