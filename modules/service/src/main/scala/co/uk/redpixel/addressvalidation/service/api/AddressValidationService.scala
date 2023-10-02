package co.uk.redpixel.addressvalidation.service.api

import co.uk.redpixel.addressvalidator.api.{
  AddressContactDetails,
  AddressValidationApi,
  ValidateOutput
}
import co.uk.redpixel.addressvalidator.AddressValidator

class AddressValidationService[F[_]](validator: AddressValidator) extends AddressValidationApi[F]:

  override def validate(address: AddressContactDetails): F[ValidateOutput] =
    validator.validate(address.fromApi)
