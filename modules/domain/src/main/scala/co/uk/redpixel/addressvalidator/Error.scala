package co.uk.redpixel.addressvalidator

import co.uk.redpixel.addressvalidator.Address.CountryCode

case class ValidationError(field: String, message: String)

enum ServiceError(message: String) extends Throwable(message):
  case UnsupportedCountryError(countryCode: CountryCode)
      extends ServiceError(s"Country $countryCode is not supported")
