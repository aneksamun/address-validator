package co.uk.redpixel.addressvalidator

import cats.syntax.apply.*
import co.uk.redpixel.addressvalidator.Rule.MaxLength
import io.circe.{Decoder, HCursor}
import monix.newtypes.*
import monix.newtypes.integrations.DerivedCirceCodec

import scala.util.matching.Regex

case class Rule(
    maxLength: MaxLength,
    required: Boolean,
    pattern: Option[Regex]
)

object Rule:

  type MaxLength = MaxLength.Type

  object MaxLength extends NewtypeValidated[Int] with DerivedCirceCodec:

    def apply(value: Int): Either[BuildFailure[Type], Type] =
      Either.cond(
        value > 0,
        unsafeCoerce(value),
        BuildFailure("Max length must be greater than 0")
      )

  given Decoder[Regex] = Decoder.decodeString.map(_.r)

  given Decoder[Rule] = (cursor: HCursor) => (
    cursor.downField("maxLength").get[MaxLength]("value"),
    cursor.downField("required").getOrElse[Boolean]("value")(fallback = false),
    cursor.downField("pattern").get[Option[Regex]]("value")
  ).mapN(Rule.apply)
