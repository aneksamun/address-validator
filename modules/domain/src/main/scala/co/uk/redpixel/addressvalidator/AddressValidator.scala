package co.uk.redpixel.addressvalidator

import cats.data.{Chain, NonEmptyChain, Validated, ValidatedNec}
import cats.effect.Async
import cats.syntax.all.*
import co.uk.redpixel.addressvalidator.Address.CountryCode
import co.uk.redpixel.addressvalidator.AddressValidator.*
import co.uk.redpixel.addressvalidator.Rule.MaxLength
import co.uk.redpixel.addressvalidator.ServiceError.*

import scala.compiletime.constValueTuple
import scala.deriving.Mirror
import scala.deriving.Mirror.ProductOf
import scala.util.matching.Regex

trait AddressValidator:

  def validate(
      country: CountryCode,
      address: Address,
      addressee: Addressee
  ): Either[ServiceError, ValidationResult]

object AddressValidator:

  type ValidationResult = ValidatedNec[ValidationError, Unit]

  private inline def extractLabelsWithValues[A <: Product](
      a: A
  )(using m: Mirror.Of[A], p: Mirror.ProductOf[A]) =
    val labels = constValueTuple[m.MirroredElemLabels]
    val values = Tuple.fromProductTyped(a)
    labels.toList zip values.toList

  private def assert(predicate: Boolean, error: => ValidationError) =
    Validated.condNec(predicate, (), error)

  private def validateOptionality(field: String, value: Option[String], required: Boolean) =
    assert(!required || value.nonEmpty, ValidationError(field, "Field is required"))

  private def validateLength(field: String, value: Option[String], maxLength: MaxLength) =
    assert(
      value.forall(_.length < maxLength.value),
      ValidationError(field, s"Value must be less than $maxLength")
    )

  private def validateRegex(field: String, value: Option[String], maybePattern: Option[Regex]) =
    assert(
      maybePattern.fold(ifEmpty = true): pattern =>
        value.forall(pattern.matches),
      ValidationError(field, "Value does not match pattern")
    )

  private def verify(
      addressee: Addressee,
      address: Address,
      fieldRules: FieldRules
  ): ValidatedNec[ValidationError, Unit] =
    NonEmptyChain
      .fromChainUnsafe(
        Chain.fromSeq(
          extractLabelsWithValues(addressee) ++
            extractLabelsWithValues(address)
        )
      )
      .map: (field, value) =>
        val rule = fieldRules(field)
        validateOptionality(field, value, rule.required) *>
          validateLength(field, value, rule.maxLength) *>
          validateRegex(field, value, rule.pattern)
      .sequence_

  def apply[F[_]: Async](): F[AddressValidator] =
    for rules <- FieldRules.load[F]
    yield new AddressValidator:
      override def validate(
          country: CountryCode,
          address: Address,
          addressee: Addressee
      ): Either[ServiceError, ValidationResult] =
        rules
          .get(country.toLowerCase)
          .toRight(UnsupportedCountryError(country))
          .map(verify(addressee, address, _))

end AddressValidator
