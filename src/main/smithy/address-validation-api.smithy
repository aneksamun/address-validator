$version: "2.0"

namespace co.uk.redpixel.address.validation

use smithy4s.api#simpleRestJson
use co.uk.redpixel.address.validation#Address
use co.uk.redpixel.address.validation#Addressee

@simpleRestJson
service AddressValidator {
  version: "1.0.0",
  operations: [Validate]
}

@http(method: "POST", uri: "/address/{country}/validate", code: 200)
operation Validate {
  input: AddressValidationRequest
}

structure AddressValidationRequest {
  @httpLabel
  @required
  country: String,

  addressee: Addressee

  address: Address
}
