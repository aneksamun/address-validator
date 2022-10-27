package co.uk.redpixel.validator.data

import co.uk.redpixel.validator.data.Rules.MaxLength
import monix.newtypes._
import monix.newtypes.integrations.DerivedCirceCodec

import scala.util.matching.Regex


final case class Rules(maxLength: MaxLength,
                       required: Boolean,
                       pattern: Option[Regex])

object Rules {

  type MaxLength = MaxLength.Type
  object MaxLength extends NewtypeValidated[Int] with DerivedCirceCodec {

    def apply(value: Int): Either[BuildFailure[Type], Type] =
      Either.cond(
        value > 0,
        unsafeCoerce(value),
        BuildFailure("Max length must be greater than 0")
      )
  }

}