package co.uk.redpixel.addressvalidator

import cats.effect.kernel.Async
import cats.effect.Resource
import cats.syntax.traverse.*
import fs2.text
import io.circe.{Decoder, HCursor, Json}
import io.circe.fs2.{decoder, stringArrayParser}
import io.github.classgraph.{ClassGraph, Resource as ClassgraphResource}

import scala.jdk.CollectionConverters.*

type FieldName  = String
type FieldRules = Map[FieldName, Rule]

object FieldRules:

  def load[F[_]: Async] = fs2.Stream
    .resource(
      Resource.fromAutoCloseable(
        Async[F].delay(
          new ClassGraph()
            .acceptPathsNonRecursive("/templates")
            .scan()
        )
      )
    )
    .flatMap: scanResult =>
      fs2.Stream.emits(
        scanResult
          .getResourcesWithExtension("json")
          .asScala
          .toVector
      )
    .map: resource =>
      fs2.Stream.emit(resource.baseName) zip
        fs2.io
          .readInputStream(
            Async[F].delay(resource.open()),
            chunkSize = 64 * 1024
          )
          .through(text.utf8.decode)
          .through(stringArrayParser)
          .through(decoder[F, FieldRules])
    .parJoinUnbounded
    .compile
    .to(Map)

  given Decoder[FieldRules] = (cursor: HCursor) =>
    cursor
      .get[Vector[Json]]("fields")
      .flatMap(
        _.traverse: json =>
          val cursor = json.hcursor
          for
            name  <- cursor.get[FieldName]("name")
            rules <- cursor.get[Rule]("rules")
          yield (name, rules)
        .map(_.toMap)
      )

  extension (underlying: ClassgraphResource)
    private def baseName: String =
      underlying.getPath
        .split("\\W+")
        .takeRight(2)
        .head

end FieldRules
