package co.uk.redpixel.addressvalidator

import cats.data.{Validated, ValidatedNec}

type ValidationResult = ValidatedNec[ValidationError, Unit]

object ValidationResult:

  val valid: ValidationResult = Validated.validNec(())

  def invalid(error: ValidationError): ValidationResult = 
    Validated.invalidNec(error)

  def cond(predicate: Boolean, ifFalse: => ValidationError): ValidationResult =
    Validated.condNec(predicate, (), ifFalse)
