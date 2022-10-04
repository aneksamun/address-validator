package co.uk.redpixel.address.validation.data

import scala.util.matching.Regex

final case class Rule(maxLength: PosInt,
                      required: Boolean = false,
                      pattern: Option[Regex] = None)

final case class FieldRule(name: String, rules: Rule)
