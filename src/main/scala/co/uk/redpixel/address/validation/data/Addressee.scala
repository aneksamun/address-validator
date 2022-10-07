package co.uk.redpixel.address.validation.data

import monix.newtypes.NewtypeWrapped

object Addressee {

  type FirstName = FirstName.Type
  object FirstName extends NewtypeWrapped[String]

  type LastName = LastName.Type
  object LastName extends NewtypeWrapped[String]

}
