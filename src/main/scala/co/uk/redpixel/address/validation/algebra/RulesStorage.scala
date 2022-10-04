package co.uk.redpixel.address.validation.algebra

import co.uk.redpixel.address.validation.data.{Country, FieldRule}

trait RulesStorage[F[_]] {

  def get(country: Country): F[Vector[FieldRule]]
}
