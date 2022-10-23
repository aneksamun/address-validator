package co.uk.redpixel.validator.rules

import cats.implicits._
import co.uk.redpixel.validator.address.Country
import co.uk.redpixel.validator.rules.ValidationRules.FieldRules
import io.circe.{Decoder, HCursor, Json}

trait ValidationRules[F[_]] {
  def get(country: Country): F[FieldRules]
}

object ValidationRules {

  type FieldName = String
  type FieldRules = Map[FieldName, Rule]

  implicit val fieldRulesDecoder: Decoder[FieldRules] = (cursor: HCursor) =>
    cursor.get[Vector[Json]]("fields").flatMap(
      _.traverse { json =>
        val cursor = json.hcursor
        for {
          name  <- cursor.get[FieldName]("name")
          rules <- cursor.get[Rule]("rules")
        } yield (name, rules)
      }.map(_.toMap)
    )
}
