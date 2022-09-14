package co.uk.redpixel.address.validation.service 

class InMemoryRulesStorage[F[_]] extends RulesStorage[F]:
  def get(country: Country): F[Vector[FieldRule]]

