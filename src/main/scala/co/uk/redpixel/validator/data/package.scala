package co.uk.redpixel.validator

import cats.implicits._
import co.uk.redpixel.validator.data.Rules.MaxLength
import io.circe.{Decoder, HCursor, Json}

import scala.util.matching.Regex

package object data {

  type FieldName  = String
  type FieldRules = Map[FieldName, Rules]

  //-- serialisation

  implicit val regexDecoder: Decoder[Regex] = Decoder.decodeString.map(_.r)

  implicit val rulesDecoder: Decoder[Rules] = (cursor: HCursor) => (
    cursor.downField("maxLength").get[MaxLength]("value"),
    cursor.downField("required").getOrElse[Boolean]("value")(fallback = false),
    cursor.downField("pattern").get[Option[Regex]]("value")
  ) mapN Rules.apply

  implicit val fieldRulesDecoder: Decoder[FieldRules] = (cursor: HCursor) =>
    cursor.get[Vector[Json]]("fields").flatMap(
      _.traverse { json =>
        val cursor = json.hcursor
        for {
          name <- cursor.get[FieldName]("name")
          rules <- cursor.get[Rules]("rules")
        } yield (name, rules)
      }.map(_.toMap)
    )
}
