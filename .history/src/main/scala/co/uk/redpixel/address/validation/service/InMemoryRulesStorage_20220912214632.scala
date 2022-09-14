package co.uk.redpixel.address.validation.service

import co.uk.redpixel.address.validation.algebra.RulesStorage

class InMemoryRulesStorage[F[_]] extends RulesStorage[F]:

  def get(country: Country): F[Vector[FieldRule]]

