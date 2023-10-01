package co.uk.redpixel.addressvalidator

import cats.data.{Chain, NonEmptyChain, Validated}
import cats.effect.Async
import cats.syntax.all.*
import co.uk.redpixel.addressvalidator.AddressContactInfo.*
import co.uk.redpixel.addressvalidator.Rule.MaxLength

import scala.compiletime.constValueTuple
import scala.deriving.Mirror
import scala.deriving.Mirror.ProductOf
import scala.util.matching.Regex

trait AddressValidator:
  def validate(
      addressContactInfo: AddressContactInfo
  ): ValidationResult

object AddressValidator:

  private inline def extractLabelsWithValues[A <: Product](
      a: A
  )(using m: Mirror.Of[A], p: Mirror.ProductOf[A]) =
    val labels = constValueTuple[m.MirroredElemLabels]
    val values = Tuple.fromProductTyped(a)
    labels.toList zip values.toList

  private def validateOptionality(field: String, value: Option[String], required: Boolean) =
    ValidationResult.cond(
      !required || value.nonEmpty,
      ValidationError.requiredFieldMissing(field)
    )

  private def validateLength(field: String, value: Option[String], maxLength: MaxLength) =
    ValidationResult.cond(
      value.forall(_.length <= maxLength.value),
      ValidationError.invalidLength(field, maxLength)
    )

  private def validateRegex(field: String, value: Option[String], maybePattern: Option[Regex]) =
    ValidationResult.cond(
      maybePattern.fold(ifEmpty = true): pattern =>
        value.forall(pattern.matches),
      ValidationError.invalidField(field)
    )

  def apply[F[_]: Async](): F[AddressValidator] =
    for rules <- FieldRules.load[F]
    yield new AddressValidator:
      override def validate(
          addressContactInfo: AddressContactInfo
      ): ValidationResult =
        Validated
          .fromOption(
            addressContactInfo.countryCode.flatMap: countryCode =>
              rules.get(countryCode.toLowerCase),
            NonEmptyChain.one(
              ValidationError.invalidCountryCode(addressContactInfo.countryCode)
            )
          )
          .andThen: fieldRules =>
            NonEmptyChain
              .fromChainUnsafe(
                Chain.fromSeq(extractLabelsWithValues(addressContactInfo))
              )
              .map: (field, value) =>
                fieldRules
                  .get(field)
                  .map: rule =>
                    validateOptionality(field, value, rule.required) *>
                      validateLength(field, value, rule.maxLength) *>
                      validateRegex(field, value, rule.pattern)
                  .getOrElse(ValidationResult.valid)
              .sequence_

end AddressValidator
