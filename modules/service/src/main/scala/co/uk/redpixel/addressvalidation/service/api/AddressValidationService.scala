package co.uk.redpixel.addressvalidation.service.api

import cats.{Applicative, ApplicativeError, Functor, MonadError, MonadThrow}
import cats.syntax.all.*
import co.uk.redpixel.addressvalidation.service.api.AddressValidationService.*
import co.uk.redpixel.addressvalidation.service.ServiceError
import co.uk.redpixel.addressvalidation.service.ServiceError.*
import co.uk.redpixel.addressvalidator.{AddressContactInfo, AddressValidator, ValidationResult}
import co.uk.redpixel.addressvalidator.api.{
  AddressContactDetails,
  AddressValidationApi,
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

  extension (details: AddressContactDetails)
    def asInfo: Either[ServiceError, AddressContactInfo] =
      details.countryCode
        .map: code =>
          Try(code.value.asInstanceOf[CountryCode]).toEither
            .leftMap(InvalidCountryCodeError(code.value))
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
            countryCode = countryCode
          )

  extension (validationResult: ValidationResult) // TODO: use chimney
    def toValidateOutput: ValidateOutput =
      ValidateOutput(
        isValid = validationResult.isValid,
        errors = validationResult.leftMap: nec =>
          nec.map: error =>
            ValidationError(
              field = error.field,
              message = error.message
            )
      )

end AddressValidationService
