package co.uk.redpixel.address.validation.data

import co.uk.redpixel.address.validation.data.Rule.MaxLength
import monix.newtypes._
import scala.util.matching.Regex

final case class Rule(maxLength: MaxLength,
                      required: Boolean = false,
                      pattern: Option[Regex] = None)

object Rule {

  type MaxLength = MaxLength.Type

  object MaxLength extends NewtypeValidated[Int] {
    def apply(value: Int): Either[BuildFailure[Type], Type] =
      Either.cond(
        value > 0,
        unsafeCoerce(value),
        BuildFailure("Max length must be greater than 0")
      )
  }
}

final case class FieldRule(name: String, rules: Rule)
