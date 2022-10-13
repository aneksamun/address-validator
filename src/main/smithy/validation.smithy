$version: "2.0"

namespace co.uk.redpixel.address.validation.api

use smithy4s.api#simpleRestJson
use co.uk.redpixel.address.validation.api#Address
use co.uk.redpixel.address.validation.api#Addressee
use co.uk.redpixel.address.validation.api#Country

@simpleRestJson
service AddressValidationService {
  version: "1.0.0",
  operations: [Validate]
}

@http(method: "POST", uri: "/address/{country}/validate", code: 200)
@idempotent
@documentation(
  "Validates address for given country"
)
operation Validate {
  input: AddressValidationRequest
}

@documentation("The address validation request")
structure AddressValidationRequest {
  @httpLabel
  @required
  country: Country,

  @required
  addressee: Addressee

  @required
  address: Address
}
