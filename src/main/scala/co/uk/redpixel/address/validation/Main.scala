package co.uk.redpixel.address.validation

import cats.effect._
import com.comcast.ip4s._
import org.http4s.ember.server._

object Main extends IOApp.Simple {

  def run: IO[Unit] =
    EmberServerBuilder
      .default[IO]
      .withPort(port"9000")
      .withHost(host"localhost")
      .build
      .useForever

}
