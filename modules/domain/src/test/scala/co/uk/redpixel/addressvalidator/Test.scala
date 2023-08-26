package co.uk.redpixel.addressvalidator

import weaver.SimpleIOSuite

object Test extends SimpleIOSuite:

  //  inline def labels[A](using m: Mirror.Of[A]) =
  //    getElemLabels[m.MirroredElemLabels]
  //
  //  inline def getElemLabels[A <: Tuple]: List[String] = inline erasedValue[A] match
  //      case _: EmptyTuple => Nil // stop condition - the tuple is empty
  //      case _: (head *:
  //            tail) => // yes, in scala 3 we can match on tuples head and tail to deconstruct them step by step
  //        val headElementLabel = constValue[head].toString // bring the head label to value space
  //        val tailElementLabels =
  //          getElemLabels[tail]                 // recursive call to get the labels from the tail
  //        headElementLabel :: tailElementLabels // concat head + tail
  //
  //  inline def getTypeclassInstances[A <: Tuple]: List[Option[String]] = inline erasedValue[A] match {
  //    case _: EmptyTuple => Nil
  //    case _: (head *: tail) =>
  //      val optionalValue = summonInline[head.asInstanceOf[Option[String]]
  //      val tailTypeClasses = getTypeclassInstances[tail]
  //      optionalValue :: getTypeclassInstances[tail]
  //  }
  //
  //  inline def summonInstancesHelper[A](using m: Mirror.Of[A]) =
  //    getTypeclassInstances[m.MirroredElemTypes]


  pureTest("test"):

    val c = AddressValidator.extractLabelsWithValues(
      Address(
        line1 = Some("James"),
        line2 = None,
        line3 = None,
        postcode = None,
        town = None,
        county = None,
        country = None,
        countryCode = None
      )
    )

    println(c)

    expect(c != null)

end Test
