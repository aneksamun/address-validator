$version: "2.0"

namespace co.uk.redpixel.address.validation.model

structure Addressee {

  firstName: String,

  lastName: String
}

structure Address {

  addressLine1: String,

  addressLine2: String,

  addressLine3: String,

  town: String,

  county: String,

  postcode: String,

  countryCode: String
}
