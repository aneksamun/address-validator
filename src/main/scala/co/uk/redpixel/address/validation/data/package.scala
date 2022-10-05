package co.uk.redpixel.address.validation

import monix.newtypes._

package object data {

  type Country = Country.Type

  object Country extends NewtypeWrapped[String]

  type MaxLength = MaxLength.Type

  object MaxLength extends NewtypeValidated[Int] {
    def apply(value: Int): Either[BuildFailure[Type], Type] =
      Either.cond(value > 0, unsafeCoerce(value), BuildFailure("Max length must be greater than 0"))
  }

}
