package co.uk.redpixel.addressvalidator

trait Validator:
  def validate(address: Address): Boolean

object Validator:
  def apply(): Validator =
    (address: Address) => true