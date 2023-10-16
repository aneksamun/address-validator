package co.uk.redpixel.addressvalidator

import cats.derived.*
import cats.syntax.apply.*
import cats.Eq
import co.uk.redpixel.addressvalidator.Rule.*
import io.circe.{Decoder, HCursor}
import monix.newtypes.*
import monix.newtypes.integrations.DerivedCirceCodec

import scala.compiletime.error
import scala.compiletime.requireConst
import scala.util.matching.Regex

case class Rule(
    maxLength: MaxLength,
    required: Boolean,
    pattern: Option[Regex] = None
) derives Eq

object Rule:

  type MaxLength = MaxLength.Type

  object MaxLength extends NewtypeValidated[Int] with DerivedCirceCodec:

    def apply(value: Int): Either[BuildFailure[Type], Type] =
      Either.cond(
        value > 0,
        unsafeCoerce(value),
        BuildFailure("Max length must be greater than 0")
      )

    inline def of(value: Int): MaxLength =
      requireConst(value)
      inline if value > 0 then unsafeCoerce(value) else error("Max length must be greater than 0")

  // -- eq

  given Eq[MaxLength] = Eq.fromUniversalEquals

  given Eq[Regex] = Eq.instance: (r1, r2) =>
    r1.pattern.pattern() == r2.pattern.pattern()

  // -- decoder

  given Decoder[Regex] = Decoder.decodeString.map(_.r)

  given Decoder[Rule] = (cursor: HCursor) =>
    (
      cursor.downField("maxLength").get[MaxLength]("value"),
      cursor.downField("required").getOrElse[Boolean]("value")(fallback = false),
      cursor.downField("pattern").get[Option[Regex]]("value")
    ).mapN(Rule.apply)

end Rule
