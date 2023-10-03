package co.uk.redpixel.addressvalidation.service.api

import cats.Applicative
import co.uk.redpixel.addressvalidation.service.api.AddressValidationService.{*, given}
import co.uk.redpixel.addressvalidator.{AddressContactInfo, AddressValidator, ValidationResult}
import co.uk.redpixel.addressvalidator.api.{
  AddressContactDetails,
  AddressValidationApi,
  ValidateOutput
}

class AddressValidationService[F[_]: Applicative](validator: AddressValidator)
    extends AddressValidationApi[F]:

  override def validate(address: AddressContactDetails): F[ValidateOutput] =
    Applicative[F].pure(validator.validate(address).toValidateOutput)

object AddressValidationService:

  given Conversion[AddressContactDetails, AddressContactInfo] with
    def apply(address: AddressContactDetails): AddressContactInfo =
      AddressContactInfo(
        addresseeFirstName = address.firstName.map(_.value),
        addresseeLastName = address.lastName.map(_.value),
        addressLine1 = address.addressLine1.map(_.value),
        addressLine2 = address.addressLine2.map(_.value),
        addressLine3 = address.addressLine3.map(_.value),
        town = address.town.map(_.value),
        county = address.county.map(_.value),
        postcode = address.postcode.map(_.value),
        countryCode = ???
      )

  extension (validationResult: ValidationResult)
    def toValidateOutput: ValidateOutput =
      ValidateOutput(
        isValid = validationResult.isValid,
        errors = ???
      )
