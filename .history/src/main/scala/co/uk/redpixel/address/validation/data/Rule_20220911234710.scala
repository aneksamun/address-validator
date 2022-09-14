package co.uk.redpixel.address.validation.data

import eu.timepit.refined.types.numeric.PosInt

import scala.util.matching.Regex

case class Rule(maxLength: PosInt, pattern: Option[Regex], required: Boolean = false)
