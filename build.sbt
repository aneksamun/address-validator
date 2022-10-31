import smithy4s.codegen.Smithy4sCodegenPlugin

ThisBuild / scalaVersion := "2.13.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "co.uk.redpixel"
ThisBuild / organizationName := "redpixel"

lazy val root = (project in file("."))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    name := "address-validator",
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.14.3",
      "io.circe" %% "circe-parser" % "0.14.3",
      "io.circe" %% "circe-generic" % "0.14.3",
      "io.circe" %% "circe-fs2" % "0.14.0",
      "io.monix" %% "newtypes-core" % "0.2.3",
      "io.monix" %% "newtypes-circe-v0-14" % "0.2.3",
      "org.typelevel" %% "cats-core" % "2.8.0",
      "org.typelevel" %% "cats-effect" % "3.3.14",
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s" % smithy4sVersion.value,
      "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger" % smithy4sVersion.value,
      "org.http4s" %% "http4s-ember-server" % "0.23.16",
      "io.github.classgraph" % "classgraph" % "4.8.149",
      "org.scalatest" %% "scalatest" % "3.2.12" % Test
    )
  )
