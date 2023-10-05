package co.uk.redpixel.addressvalidation.service

import cats.syntax.option.*

enum ServiceError(message: String, cause: Option[Throwable] = None)
    extends Throwable(message, cause.orNull):
  case InvalidCountryCodeError(countryCode: String)(throwable: Throwable)
      extends ServiceError(s"Country $countryCode is not supported", throwable.some)
