package co.uk.redpixel.addressvalidator

import cats.derived.*
import cats.Eq
import co.uk.redpixel.addressvalidator.AddressContactInfo.CountryCode
import co.uk.redpixel.addressvalidator.Rule.MaxLength

case class ValidationError(field: String, message: String) derives Eq

object ValidationError:

  def invalidCountryCode(countryCode: Option[CountryCode]): ValidationError =
    ValidationError(
      "countryCode",
      countryCode.fold("Country code is required"): code =>
        s"Country code $code is not supported"
    )

  def requiredFieldMissing(field: String): ValidationError =
    ValidationError(field, "Field is required")

  def invalidLength(field: String, maxLength: MaxLength): ValidationError =
    ValidationError(field, s"Field length must be less or equal to $maxLength")

  def invalidField(field: String): ValidationError =
    ValidationError(field, "Value does not match pattern")
