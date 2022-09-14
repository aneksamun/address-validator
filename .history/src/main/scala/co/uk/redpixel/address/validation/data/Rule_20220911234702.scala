package co.uk.redpixel.address.validation.data

import eu.timepit.refined.types.numeric.PosInt

import scala.util.matching.Regex

case class Rule(required: Boolean = false, maxLength: PosInt, pattern: Option[Regex])