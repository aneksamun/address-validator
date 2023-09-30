package co.uk.redpixel.addressvalidator

import cats.derived.*
import cats.Eq
import co.uk.redpixel.addressvalidator.Addressee.*

case class Addressee(
    firstName: Option[FirstName],
    lastName: Option[LastName]
) derives Eq

object Addressee:
  type FirstName = String
  type LastName  = String
