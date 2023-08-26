package co.uk.redpixel.addressvalidator

import cats.data.ValidatedNec
import cats.effect.Async
import cats.syntax.all.*
import co.uk.redpixel.addressvalidator.Address.CountryCode
//import cats.syntax.validated.*
import co.uk.redpixel.addressvalidator.AddressValidator.*

import scala.compiletime.constValueTuple
import scala.deriving.Mirror
import scala.deriving.Mirror.ProductOf

trait AddressValidator:

  def validate(
      country: CountryCode,
      address: Address,
      addressee: Addressee
  ): Either[
    UnsupportedCountryError,
    ValidationResult
  ]

object AddressValidator:

  type ValidationResult = ValidatedNec[ValidationError, Unit]

  case class ValidationError(field: String, message: String)

  case class UnsupportedCountryError(countryCode: CountryCode)
      extends Throwable(s"Country $countryCode is not supported")

  private inline def extractLabelsWithValues[A <: Product](
      a: A
  )(using m: Mirror.Of[A], p: Mirror.ProductOf[A]) =
    val labels = constValueTuple[m.MirroredElemLabels]
    val values = Tuple.fromProductTyped(a)
    labels.toList zip values.toList

  def apply[F[_]: Async](): F[AddressValidator] =
    for rules <- FieldRules.load[F]
    yield new AddressValidator:
      override def validate(
          country: CountryCode,
          address: Address,
          addressee: Addressee
      ): Either[UnsupportedCountryError, ValidationResult] =
        rules
          .get(country.toLowerCase)
          .toRight(UnsupportedCountryError(country))
          .map: fieldRules =>
            extractLabelsWithValues(address)
            println(fieldRules)
            ???

      ???

end AddressValidator
//          .map(_.validate(address, addressee))
//          .flatMap(MonadError[F, Throwable].fromEither)
//          .toValidatedNec
