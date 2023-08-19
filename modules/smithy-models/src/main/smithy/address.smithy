$version: "2.0"

namespace co.uk.redpixel.addressvalidator.api

@documentation("The first line of address")
string AddressLine1

@documentation("The second line of address")
string AddressLine2

@documentation("The third line of address")
string AddressLine3

@documentation("A town of address")
string Town

@documentation("A county of address")
string County

@documentation("A postcode of address")
string Postcode

@documentation("The country code of address")
string CountryCode

@documentation("The country of address")
string Country

@documentation("The address details")
structure Address {

  addressLine1: AddressLine1

  addressLine2: AddressLine2

  addressLine3: AddressLine3

  town: Town

  county: County

  postcode: Postcode

  countryCode: CountryCode
}
