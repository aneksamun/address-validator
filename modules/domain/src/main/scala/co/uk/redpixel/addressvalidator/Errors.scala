package co.uk.redpixel.addressvalidator

import co.uk.redpixel.addressvalidator.Address.CountryCode

case class ValidationError(
    field: String,
    message: String
)

case class UnsupportedCountryError(countryCode: CountryCode)
    extends Throwable(s"Country $countryCode is not supported")
