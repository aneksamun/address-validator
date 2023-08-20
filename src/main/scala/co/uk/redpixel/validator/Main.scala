package co.uk.redpixel.validator

//import cats.effect._
//import co.uk.redpixel.validator.data.Address._
//import co.uk.redpixel.validator.data.{Address, FieldRules}
//import fs2.text
//import io.circe.fs2.{decoder, stringArrayParser}
//import io.github.classgraph.{ClassGraph, Resource => ClassgraphResource}
//import shapeless.labelled.FieldType
//
//import scala.jdk.CollectionConverters._

// import com.comcast.ip4s._
// import org.http4s.ember.server._

@main def main(): Unit =
  println("Hello world!")

//object Main extends IOApp.Simple {
//
//
//
//  def run: IO[Unit] =
//    IO
//    for {
//    validated <- IO(
//      new AddressValidator(Map.empty).validate(
//        Address(
//          line1 = Some("James"),
//          None,
//          None,
//          None,
//          None,
//          None,
//          None,
//          None
//        )
//      )
//    )
//  } yield println(validated)

  //  EmberServerBuilder
  //    .default[IO]
  //    .withPort(port"9000")
  //    .withHost(host"localhost")
  //    .build
  //    .useForever

//class AddressValidator() {
//
//  import shapeless._
//  import shapeless.syntax.std.product._
//
//  object print extends Poly1 {
//
//    implicit def caseString[K <: Symbol](implicit witness: Witness.Aux[K]): Case.Aux[FieldType[K, Option[String]], Boolean] =
//      at[FieldType[K, Option[String]]] { value =>
//        val fieldRules = rules(witness.value.name)
//        if (fieldRules.required)
//          value.isDefined
//        else true
//        value.isDefined
//      }
//
//  }
//
//  def validate(address: Address): Boolean = {
//
//    // 1. Build a map of field rules per region (ok)
//    // 2. Find a way to extract field names from the value class
//    // 3. Find a way to extract field values from the value class
//
//    val a = address.toRecord
//
//    a.map(print).toList[Boolean].forall(identity)
//  }
//
//}
