package co.uk.redpixel.addressvalidator

import cats.Eq
import co.uk.redpixel.addressvalidator.AddressContactInfo.*

case class AddressContactInfo(
    addresseeFirstName: Option[FirstName],
    addresseeLastName: Option[LastName],
    addressLine1: Option[Line1],
    addressLine2: Option[Line2],
    addressLine3: Option[Line3],
    town: Option[Town],
    county: Option[County],
    postcode: Option[Postcode],
    countryCode: Option[CountryCode]
)

object AddressContactInfo:
  type FirstName   = String
  type LastName    = String
  type Line1       = String
  type Line2       = String
  type Line3       = String
  type Postcode    = String
  type Town        = String
  type County      = String
  type CountryCode = "UK" | "US"

  given Eq[AddressContactInfo] = Eq.fromUniversalEquals
