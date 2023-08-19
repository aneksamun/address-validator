package co.uk.redpixel.addressvalidator

import co.uk.redpixel.addressvalidator.Addressee.*

case class Addressee(firstName: FirstName, lastName: LastName)

object Addressee:
  type FirstName = String
  type LastName  = String
