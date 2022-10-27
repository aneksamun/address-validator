package co.uk.redpixel.validator.data

import co.uk.redpixel.validator.data.Addressee._
import monix.newtypes.NewtypeWrapped

final case class Addressee(firstName: FirstName, lastName: LastName)

object Addressee {

  type FirstName = FirstName.Type
  object FirstName extends NewtypeWrapped[String]

  type LastName = LastName.Type
  object LastName extends NewtypeWrapped[String]

}
