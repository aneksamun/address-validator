package co.uk.redpixel.address.validation

import monix.newtypes._

package object data {

  type Country = Country.Type
  object Country extends NewtypeWrapped[String]

  type PosInt = PosInt.Type
  object PosInt extends NewtypeWrapped[Int]
}
