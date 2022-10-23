package co.uk.redpixel.validator

import monix.newtypes._

package object addressee {

  type FirstName = FirstName.Type
  object FirstName extends NewtypeWrapped[String]

  type LastName = LastName.Type
  object LastName extends NewtypeWrapped[String]
}
