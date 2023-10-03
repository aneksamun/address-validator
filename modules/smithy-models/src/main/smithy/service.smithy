$version: "2.0"

namespace co.uk.redpixel.addressvalidator.api

use alloy#simpleRestJson
use co.uk.redpixel.addressvalidator.api#AddressContactDetails

@simpleRestJson
service AddressValidationApi {
  version: "v1"
  operations: [Validate]
  errors: []
}

@http(method: "POST", uri: "/validate", code: 200)
@idempotent
@documentation("Validates address")
operation Validate {
  input := {
    @required
    address: AddressContactDetails
  }
  output := {
    @required
    isValid: Boolean
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
