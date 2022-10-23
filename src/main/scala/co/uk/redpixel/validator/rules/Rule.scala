package co.uk.redpixel.validator.rules

import cats.implicits.catsSyntaxTuple3Semigroupal
import co.uk.redpixel.validator.rules.Rule.MaxLength
import io.circe.{Decoder, HCursor}
import monix.newtypes._
import monix.newtypes.integrations.DerivedCirceCodec

import scala.util.matching.Regex

final case class Rule(maxLength: MaxLength,
                      required: Boolean,
                      pattern: Option[Regex])

object Rule {

  type MaxLength = MaxLength.Type

  object MaxLength extends NewtypeValidated[Int] with DerivedCirceCodec {
    def apply(value: Int): Either[BuildFailure[Type], Type] =
      Either.cond(
        value > 0,
        unsafeCoerce(value),
        BuildFailure("Max length must be greater than 0")
      )
  }

  implicit val regexDecoder: Decoder[Regex] =
    Decoder.decodeString.map(_.r)

  implicit val testRuleDecoder: Decoder[Rule] = (cursor: HCursor) => (
    cursor.downField("maxLength").get[MaxLength]("value"),
    cursor.downField("required").getOrElse[Boolean]("value")(fallback = false),
    cursor.downField("pattern").get[Option[Regex]]("value")
  ) mapN Rule.apply
}
