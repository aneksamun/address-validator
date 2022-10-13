package co.uk.redpixel.address.validation.service

import co.uk.redpixel.address.validation.api.AddressValidationService

object AddressValidator {

  def apply[F[_]](implicit ev: AddressValidationService[F]): AddressValidationService[F] = ev

}
