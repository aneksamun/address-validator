package co.uk.redpixel.addressvalidator

import cats.data.ValidatedNec
import cats.effect.Async
import cats.syntax.all.*
import co.uk.redpixel.addressvalidator.Address.CountryCode
//import cats.syntax.validated.*
import co.uk.redpixel.addressvalidator.AddressValidator.*

trait AddressValidator:

  def validate(
      country: CountryCode,
      address: Address,
      addressee: Addressee
  ): ValidationResult

object AddressValidator:

  type ValidationResult = ValidatedNec[ValidationError, Unit]

  case class ValidationError(
      field: String,
      message: String
  )

  def apply[F[_]: Async](): F[AddressValidator] =
    for rules <- FieldRules.load[F]
    yield new AddressValidator:
      override def validate(
          country: CountryCode,
          address: Address,
          addressee: Addressee
      ): ValidationResult =
        ???

