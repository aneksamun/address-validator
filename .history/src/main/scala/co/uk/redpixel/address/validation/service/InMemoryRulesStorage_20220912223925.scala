package co.uk.redpixel.address.validation.service

import co.uk.redpixel.address.validation.algebra.RulesStorage
import co.uk.redpixel.address.validation.algebra.RulesStorage.Country
import co.uk.redpixel.address.validation.data.FieldRule

class InMemoryRulesStorage[F[_]] extends RulesStorage[F]:
  def get(country: Country): F[Vector[FieldRule]] = ???

object InMemoryRulesStorage:
  