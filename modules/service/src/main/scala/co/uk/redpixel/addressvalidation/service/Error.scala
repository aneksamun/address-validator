package co.uk.redpixel.addressvalidation.service

enum ServiceError(message: String) extends Throwable(message):
  case UnsupportedCountryError(countryCode: String)
    extends ServiceError(s"Country $countryCode is not supported")
