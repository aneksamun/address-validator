import sbt.*

object Dependencies {

  lazy val circe = Seq(
    "io.circe" %% "circe-core"    % Versions.circe,
    "io.circe" %% "circe-parser"  % Versions.circe,
    "io.circe" %% "circe-generic" % Versions.circe,
    "io.circe" %% "circe-fs2"     % Versions.circeFs2
  )

  lazy val cats = Seq(
    "org.typelevel" %% "cats-core" % Versions.cats
  )

  lazy val catsEffect = Seq(
    "org.typelevel" %% "cats-effect" % Versions.catsEffects
  )

  lazy val fs2 = Seq(
    "co.fs2" %% "fs2-core" % Versions.fs2,
    "co.fs2" %% "fs2-io"   % Versions.fs2
  )

  lazy val kittens = Seq(
    "org.typelevel" %% "kittens" % Versions.kittens
  )

  lazy val pureConfig = Seq(
    "com.github.pureconfig" %% "pureconfig-core" % Versions.pureConfig

  )

  lazy val classGraph = Seq(
    "io.github.classgraph" % "classgraph" % Versions.classGraph
  )

  lazy val monixNewTypes = Seq(
    "io.monix" %% "newtypes-core"        % Versions.monixNewTypes,
    "io.monix" %% "newtypes-circe-v0-14" % Versions.monixNewTypes
  )

  def smithy4sCore(smithy4sVersion: String) = Seq(
    "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4sVersion
  )

  def smithy4sHttp4sServer(smithy4sVersion: String) =
    smithy4sCore(smithy4sVersion) ++ Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s"         % smithy4sVersion,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger" % smithy4sVersion
    )

  lazy val http4sEmberServer = Seq(
    "org.http4s" %% "http4s-ember-server" % Versions.http4s
  )

  lazy val weaverTest = Seq(
    "com.disneystreaming" %% "weaver-cats"       % Versions.weaver % Test,
    "com.disneystreaming" %% "weaver-scalacheck" % Versions.weaver % Test
  )
}
