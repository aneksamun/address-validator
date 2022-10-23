$version: "2.0"

namespace co.uk.redpixel.validator.api

@documentation("The addressee first name")
string FirstName

@documentation("The addressee last name")
string LastName

@documentation("The addressee details")
structure Addressee {

  @required
  firstName: FirstName,

  @required
  lastName: LastName
}
