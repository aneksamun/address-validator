package co.uk.redpixel.addressvalidation.service.config

import cats.syntax.either.*
import cats.ApplicativeError
import pureconfig.*
import pureconfig.error.ConfigReaderFailures
import pureconfig.generic.derivation.ConfigReaderDerivation.Default.*

case class AddressValidationServiceConfig(http: HttpServerConfig) derives ConfigReader

object AddressValidationServiceConfig:

  class ConfigurationLoadError(failures: ConfigReaderFailures)
      extends Throwable(s"Error loading configuration. $failures")

  def load[F[_]](using ApplicativeError[F, Throwable]): F[AddressValidationServiceConfig] =
    ConfigSource.default
      .load[AddressValidationServiceConfig]
      .leftMap(ConfigurationLoadError(_))
      .liftTo[F]
