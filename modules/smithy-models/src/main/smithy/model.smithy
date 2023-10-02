$version: "2.0"

namespace co.uk.redpixel.addressvalidator.api

@documentation("The addressee first name")
string FirstName

@documentation("The addressee last name")
string LastName

@documentation("The address first line")
string AddressLine1

@documentation("The address second line")
string AddressLine2

@documentation("The address third line")
string AddressLine3

@documentation("The address town")
string Town

@documentation("The address county")
string County

@documentation("The address postcode")
string Postcode

@documentation("The address country code")
string CountryCode

@documentation("The address contact details")
structure AddressContactDetails {
  firstName: FirstName
  lastName: LastName
  addressLine1: AddressLine1
  addressLine2: AddressLine2
  addressLine3: AddressLine3
  town: Town
  county: County
  postcode: Postcode
  countryCode: CountryCode
}
