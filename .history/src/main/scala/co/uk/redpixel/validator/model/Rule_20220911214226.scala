package co.uk.redpixel.validator.model

final case class Rule(
  required: Boolean,
  maxLenght: Int,
  pattern: Regex
)
