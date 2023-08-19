$version: "2.0"

namespace co.uk.redpixel.addressvalidator.api

string FirstName

string LastName

structure Addressee {

  @required
  firstName: FirstName

  @required
  lastName: LastName
}
