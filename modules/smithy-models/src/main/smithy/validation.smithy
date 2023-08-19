$version: "2.0"

namespace co.uk.redpixel.addressvalidator.api

use smithy4s.api#simpleRestJson
use co.uk.redpixel.validator.api#Address
use co.uk.redpixel.validator.api#Addressee
use co.uk.redpixel.validator.api#Country

@simpleRestJson
service AddressValidator {
  version: "1.0.0"
  operations: [Validate]
}

@http(method: "POST", uri: "/address/{country}/validate", code: 200)
@idempotent
@documentation(
  "Validates an address for given country"
)
operation Validate {
  input: AddressValidationRequest
}

@documentation("The address validation request")
structure AddressValidationRequest {
  @httpLabel
  @required
  country: Country

  @required
  addressee: Addressee

  @required
  address: Address
}
