package co.uk.redpixel.addressvalidator

import co.uk.redpixel.addressvalidator.Address.*

case class Address(
    line1: Option[Line1],
    line2: Option[Line2],
    line3: Option[Line3],
    postcode: Option[Postcode],
    town: Option[Town],
    county: Option[County],
    country: Option[Country],
    countryCode: Option[CountryCode]
)

object Address:
  type Line1       = String
  type Line2       = String
  type Line3       = String
  type Postcode    = String
  type Town        = String
  type County      = String
  type Country     = String
  type CountryCode = String
