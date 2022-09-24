package co.uk.redpixel.address.validation.algebra

import co.uk.redpixel.address.validation.algebra.RulesStorage.Country
import co.uk.redpixel.address.validation.data.FieldRule

trait RulesStorage[F[_]]:
  def get(country: Country): F[Vector[FieldRule]]

object RulesStorage:
  opaque type Country <: String = String

  object Country:
    def apply(value: String): Country = value
