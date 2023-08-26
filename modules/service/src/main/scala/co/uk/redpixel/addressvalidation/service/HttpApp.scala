package co.uk.redpixel.addressvalidation.service

import cats.effect
import cats.effect.*
import co.uk.redpixel.addressvalidation.service.config.AddressValidationServiceConfig
import org.http4s.ember.server.*

object HttpApp extends effect.IOApp.Simple:

  def run: IO[Unit] =
    for
      config <- AddressValidationServiceConfig.load[IO]
      _ <- EmberServerBuilder
        .default[IO]
        .withHost(config.http.host)
        .withPort(config.http.port)
        .build
        .useForever
    yield ()
