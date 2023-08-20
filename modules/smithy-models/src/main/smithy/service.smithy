$version: "2.0"

namespace co.uk.redpixel.addressvalidator.api

use alloy#simpleRestJson
use co.uk.redpixel.addressvalidator.api#Address
use co.uk.redpixel.addressvalidator.api#Addressee
use co.uk.redpixel.addressvalidator.api#CountryCode

@simpleRestJson
service AddressValidationService {
  version: "v1"
  operations: [Validate]
  errors: []
}

@http(method: "POST", uri: "/validate-address", code: 200)
@idempotent
@documentation("Validates an address")
operation Validate {
  input := {
    @required
    country: CountryCode
    @required
    addressee: Addressee
    @required
    address: Address
  }
  output := {
    @required
    valid: Boolean
    errors: ValidationErrors = []
  }
}

structure ValidationError {
  @required
  field: String
  @required
  message: String
}

list ValidationErrors {
  member: ValidationError
}
