package co.uk.redpixel.addressvalidator

import cats.syntax.option.*

trait Fixtures:

  val validUkAddress = AddressContactInfo(
    addresseeFirstName = "John".some,
    addresseeLastName = "Doe".some,
    addressLine1 = "1 Main Street".some,
    addressLine2 = "Apt 1".some,
    addressLine3 = "Buckingham Palace".some,
    town = "London".some,
    county = "Greater London".some,
    postcode = "SW1A 1AA".some,
    countryCode = "UK".some
  )

  val validUsAddress = AddressContactInfo(
    addresseeFirstName = "John".some,
    addresseeLastName = "Doe".some,
    addressLine1 = "600 E".some,
    addressLine2 = None,
    addressLine3 = "University Dr".some,
    town = "Rochester".some,
    county = "Oakland County".some,
    postcode = "48307".some,
    countryCode = "US".some
  )

object Fixtures extends Fixtures
