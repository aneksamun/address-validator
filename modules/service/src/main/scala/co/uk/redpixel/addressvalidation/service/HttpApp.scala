package co.uk.redpixel.addressvalidation.service

import cats.effect.*
import com.comcast.ip4s.*
import org.http4s.ember.server.*

object HttpApp extends IOApp.Simple:

  def run: IO[Unit] =
    EmberServerBuilder
      .default[IO]
      .withPort(port"9000")
      .withHost(host"localhost")
      .build
      .useForever
