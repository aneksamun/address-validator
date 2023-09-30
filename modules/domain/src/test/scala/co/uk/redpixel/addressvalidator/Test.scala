package co.uk.redpixel.addressvalidator

import weaver.SimpleIOSuite

import scala.compiletime.constValueTuple
import scala.deriving.Mirror

object Test extends SimpleIOSuite:

  inline def extractLabelsWithValues[A <: Product](
      a: A
  )(using m: Mirror.Of[A], p: Mirror.ProductOf[A]) =
    val labels = constValueTuple[m.MirroredElemLabels]
    val values = Tuple.fromProductTyped(a)
    labels.toList zip values.toList

  pureTest("test") {
    val a =
      extractLabelsWithValues(
        Addressee(
          firstName = Some("1"),
          lastName = Some("2")
        )
      ) ++
      extractLabelsWithValues(
        Address(
          line1 = Some("1"),
          line2 = Some("2"),
          line3 = Some("3"),
          postcode = Some("4"),
          town = Some("5"),
          county = Some("6"),
          country = Some("7"),
          countryCode = Some("8")
        )
      )

    println(a)

    expect(a.nonEmpty)
  }
end Test
