$version: "2.0"

namespace co.uk.redpixel.address.validation.api

@documentation("The first line of the address")
string AddressLine1

@documentation("The second line of the address")
string AddressLine2

@documentation("The third line of the address")
string AddressLine3

@documentation("A town of the address")
string Town

@documentation("A county of the address")
string County

@documentation("A postcode of the address")
string Postcode

@documentation("The address country code")
string CountryCode

@documentation("The country of the address")
string Country

@documentation("The address details")
structure Address {

  addressLine1: AddressLine1,

  addressLine2: AddressLine2,

  addressLine3: AddressLine3,

  town: Town,

  county: County,

  postcode: Postcode,

  countryCode: CountryCode
}
