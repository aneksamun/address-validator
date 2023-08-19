package co.uk.redpixel.addressvalidator

import scala.util.matching.Regex

case class Rules(
    maxLength: MaxLength,
    required: Boolean,
    pattern: Option[Regex]
)

object Rules

