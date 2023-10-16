$version: "2.0"

namespace co.uk.redpixel.addressvalidator.api

use co.uk.redpixel.addressvalidator.api#CountryCode

@error("client")
@httpError(400)
@documentation("Invalid country code error")
structure InvalidCountryCodeError {
  @required
  countryCode: CountryCode
}
