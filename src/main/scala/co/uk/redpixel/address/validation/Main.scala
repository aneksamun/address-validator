package co.uk.redpixel.address.validation

import org.http4s.ember.server._
import cats.effect._
import com.comcast.ip4s._

object Main extends IOApp.Simple:

  val run =
    EmberServerBuilder
      .default[IO]
      .withPort(port"9000")
      .withHost(host"localhost")
      .build
      .useForever
