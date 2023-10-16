package co.uk.redpixel.addressvalidation.service

import cats.effect.*
import co.uk.redpixel.addressvalidation.service.api.AddressValidationService
import co.uk.redpixel.addressvalidation.service.config.AddressValidationServiceConfig
import org.http4s.ember.server.*

object HttpApp extends IOApp.Simple:

  def run: IO[Unit] =
    for
      config <- AddressValidationServiceConfig.load[IO]
      routes = AddressValidationService.routes <+> Docs.routes
      _ <- EmberServerBuilder
        .default[IO]
        .withHost(config.http.host)
        .withPort(config.http.port)
        .withHttpApp(routes.orNotFound)
        .withHttp2
        .build
        .useForever
    yield ()
