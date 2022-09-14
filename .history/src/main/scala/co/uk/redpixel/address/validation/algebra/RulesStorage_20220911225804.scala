package co.uk.redpixel.address.validation.algebra

import co.uk.redpixel.address.validation.data.RuleSet

trait RulesStorage:
  def get(country: String): Option[RuleSet]
