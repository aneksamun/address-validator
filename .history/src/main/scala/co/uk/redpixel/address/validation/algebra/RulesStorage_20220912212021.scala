package co.uk.redpixel.address.validation.algebra

import co.uk.redpixel.address.validation.data.FieldRule

trait RulesStorage[F[_]]:
  def get(country: Country): F[Vector[FieldRule]]

object RulesStorage:
  type Country = String
