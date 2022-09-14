package co.uk.redpixel.address.validation.data

import eu.timepit.refined.types.numeric.PosInt

import scala.util.matching.Regex

case class Rule(required: Option[Boolean], maxLength: PosInt, pattern: Option[Regex])
