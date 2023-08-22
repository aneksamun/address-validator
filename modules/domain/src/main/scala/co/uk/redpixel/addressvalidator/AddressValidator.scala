package co.uk.redpixel.addressvalidator

import cats.data.ValidatedNec
import cats.effect.Async
import cats.syntax.all.*
//import cats.MonadError
import co.uk.redpixel.addressvalidator.Address.CountryCode
//import cats.syntax.validated.*
import co.uk.redpixel.addressvalidator.AddressValidator.*

trait AddressValidator:

  def validate(
      country: CountryCode,
      address: Address,
      addressee: Addressee
  ): Either[UnsupportedCountryError, ValidationResult]

object AddressValidator:

  type ValidationResult = ValidatedNec[ValidationError, Unit]

  inline def labelsOf[A](using m: Mirror.Of[A]): m.MirroredElemLabels =
    constValueTuple[m.MirroredElemLabels]

  inline def valuesOf[A <: Product](a: A)(using
      m: Mirror.ProductOf[A]
  ): ProductOf[A]#MirroredElemTypes =
    Tuple.fromProductTyped(a)

  def apply[F[_]: Async](): F[AddressValidator] =
    for rules <- FieldRules.load[F]
    yield new AddressValidator:
      override def validate(
          country: CountryCode,
          address: Address,
          addressee: Addressee
      ): Either[UnsupportedCountryError, ValidationResult] =
        rules
          .get(country)
          .toRight(UnsupportedCountryError(country))
          .map: fieldRules =>
            ???

      ???
//          .map(_.validate(address, addressee))
//          .flatMap(MonadError[F, Throwable].fromEither)
//          .toValidatedNec
