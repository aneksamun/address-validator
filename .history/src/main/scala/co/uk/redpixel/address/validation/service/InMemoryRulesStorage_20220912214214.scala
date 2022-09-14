package co.uk.redpixel.address.validation.service 

class InMemoryRulesStorage[F[_]] private (rules: Ref[F]) extends RulesStorage[F]:

  def get(country: Country): F[Vector[FieldRule]]

