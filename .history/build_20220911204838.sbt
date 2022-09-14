
ThisBuild / scalaVersion     := "3.2.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "co.uk.redpixel"
ThisBuild / organizationName := "redpixel"

val circeVersion = "0.14.2"

lazy val root = (project in file("."))
  .settings(
    name := "address-validator",

    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.12",
      "io.circe" %% "circe-core" % circeVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-parser" % circeVersion
    )
  )
