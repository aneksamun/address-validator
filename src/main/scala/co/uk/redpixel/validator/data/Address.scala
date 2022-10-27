package co.uk.redpixel.validator.data

import co.uk.redpixel.validator.data.Address._
import monix.newtypes._

final case class Address(
  line1: Option[AddressLine1],
  line2: Option[AddressLine2],
  line3: Option[AddressLine3],
  postcode: Option[Postcode],
  town: Option[Town],
  county: Option[County],
  country: Option[Country],
  countryCode: Option[CountryCode]
)

object Address {

  type AddressLine1 = AddressLine1.Type
  object AddressLine1 extends NewtypeWrapped[String]

  type AddressLine2 = AddressLine2.Type
  object AddressLine2 extends NewtypeWrapped[String]

  type AddressLine3 = AddressLine3.Type
  object AddressLine3 extends NewtypeWrapped[String]

  type Postcode = Postcode.Type
  object Postcode extends NewtypeWrapped[String]

  type Town = Town.Type
  object Town extends NewtypeWrapped[String]

  type County = County.Type
  object County extends NewtypeWrapped[String]

  type Country = Country.Type
  object Country extends NewtypeWrapped[String]

  type CountryCode = CountryCode.Type
  object CountryCode extends NewtypeWrapped[String]

}
