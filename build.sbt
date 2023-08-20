import sbt.Keys.libraryDependencies
import smithy4s.codegen.Smithy4sCodegenPlugin

import Dependencies.*

Global / onChangedBuildSource := ReloadOnSourceChanges

inThisBuild(
  Seq(
    scalaVersion      := "3.3.0",
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalacOptions += "-Wunused:all",
    organization     := "co.uk.redpixel",
    organizationName := "Red Pixel Ltd"
  )
)

lazy val service = project
  .in(file("modules/service"))
  .dependsOn(`smithy-models`, `domain`)
  .settings(
    name := "address-validation-service",
    libraryDependencies ++= smithy4sHttp4sServer(
      smithy4sVersion.value
    ) ++ http4sEmberServer ++ cats ++ catsEffect,
    testFrameworks += new TestFramework("weaver.framework.CatsEffect")
  )

lazy val `smithy-models` = project
  .in(file("modules/smithy-models"))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    libraryDependencies ++= smithy4sCore(smithy4sVersion.value)
  )

lazy val domain = project
  .in(file("modules/domain"))
  .settings(
    name := "address-validation-domain",
    testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
    libraryDependencies ++=
      circe ++ cats ++ catsEffect ++ fs2 ++ kittens ++ classGraph ++ monixNewTypes ++ weaverTest
  )

lazy val root =
  project
    .in(file("."))
    .aggregate(
      domain,
      `smithy-models`,
      service
    )
