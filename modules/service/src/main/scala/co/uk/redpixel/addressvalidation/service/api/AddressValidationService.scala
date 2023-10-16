package co.uk.redpixel.addressvalidation.service.api

import cats.effect.kernel.Async
import cats.syntax.all.*
import cats.MonadThrow
import co.uk.redpixel.addressvalidation.service.api.AddressValidationService.*
import co.uk.redpixel.addressvalidation.service.api.InvalidCountryCodeError
import co.uk.redpixel.addressvalidator.{api, AddressContactInfo, AddressValidator, ValidationResult}
import co.uk.redpixel.addressvalidator.api.{
  AddressContactDetails,
  AddressValidationApi,
  InvalidCountryCodeError,
  ValidateOutput,
  ValidationError
}
import co.uk.redpixel.addressvalidator.AddressContactInfo.CountryCode

import scala.util.Try

class AddressValidationService[F[_]: MonadThrow] private (validator: AddressValidator)
    extends AddressValidationApi[F]:

  override def validate(address: AddressContactDetails): F[ValidateOutput] =
    MonadThrow[F]
      .fromEither(address.asInfo)
      .map: info =>
        validator.validate(info).toValidateOutput

object AddressValidationService:

  def apply[F[_]](implicit ev: AddressValidationService[F]): AddressValidationService[F] = ev

  def default[F[_]: Async]: F[AddressValidationService[F]] =
    AddressValidator().map: validator =>
      new AddressValidationService[F](validator)

  extension (details: AddressContactDetails)
    def asInfo: Either[InvalidCountryCodeError, AddressContactInfo] =
      details.countryCode
        .map: code =>
          Try(code.value.asInstanceOf[CountryCode]).toEither
            .leftMap(x => InvalidCountryCodeError(code)) // InvalidCountryCodeError
        .sequence
        .map: countryCode =>
          AddressContactInfo(
            addresseeFirstName = details.firstName.map(_.value),
            addresseeLastName = details.lastName.map(_.value),
            addressLine1 = details.addressLine1.map(_.value),
            addressLine2 = details.addressLine2.map(_.value),
            addressLine3 = details.addressLine3.map(_.value),
            town = details.town.map(_.value),
            county = details.county.map(_.value),
            postcode = details.postcode.map(_.value),
            countryCode = countryCode.map(_.asInstanceOf[CountryCode])
          )

  extension (validationResult: ValidationResult)
    def toValidateOutput: ValidateOutput =
      ValidateOutput(
        isValid = validationResult.isValid,
        errors = validationResult
          .fold(
            _.map: error =>
              ValidationError(
                field = error.field,
                message = error.message
              )
            .toList,
            _ => Nil
          )
      )

end AddressValidationService
