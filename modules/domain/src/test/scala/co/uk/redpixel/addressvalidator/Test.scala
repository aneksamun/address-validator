package co.uk.redpixel.addressvalidator

import weaver.SimpleIOSuite

import scala.deriving.Mirror
import scala.deriving.Mirror.ProductOf
//import scala.compiletime.constValue
import scala.compiletime.constValueTuple
//
//import scala.compiletime.erasedValue

object Test extends SimpleIOSuite:

//  inline def getElemLabelsHelper[A](using m: Mirror.Of[A]) =
//    getElemLabels[m.MirroredElemLabels]
//
//  inline def getElemLabels[A <: Tuple]: List[String] = inline erasedValue[A] match
//    case _: EmptyTuple => Nil // stop condition - the tuple is empty
//    case _: (head *: tail) => // yes, in scala 3 we can match on tuples head and tail to deconstruct them step by step
//      val headElementLabel = constValue[head].toString // bring the head label to value space
//      val tailElementLabels = getElemLabels[tail] // recursive call to get the labels from the tail
//      headElementLabel :: tailElementLabels // concat head + tail
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
//    val address = Address(
//      line1 = Some("James"),
//      line2 = None,
//      line3 = None,
//      postcode = None,
//      town = None,
//      county = None,
//      country = None,
//      countryCode = None
//    )

    inline def labels[A](using m: Mirror.Of[A]) =
      constValueTuple[m.MirroredElemLabels]

    inline def values[A <: Product](a: A)(using m: Mirror.ProductOf[A]): ProductOf[A]#MirroredElemTypes =
      Tuple.fromProductTyped(a)

//    val mirror = summon[Mirror.Of[Address]]
    val a = constValueTuple[mirror.MirroredElemLabels]
    val b = (Some("James"), None, None, None, None, None, None, None)

    val c = a.zip(b).toList

    println(c)

    expect(c != null)
