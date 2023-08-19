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
  )

lazy val `smithy-models` = project
  .in(file("modules/smithy-models"))
  .enablePlugins(Smithy4sCodegenPlugin)
  .settings(
    libraryDependencies ++= Seq(
      "com.disneystreaming.smithy4s" %% "smithy4s-core" % smithy4sVersion.value
    )
  )

lazy val domain = project
  .in(file("modules/domain"))
  .settings(
    name := "address-validation-domain",
    testFrameworks += new TestFramework("weaver.framework.CatsEffect"),
    libraryDependencies ++= circe ++ cats ++ catsEffect
  )

//lazy val root = (project in file("."))
//  .enablePlugins(Smithy4sCodegenPlugin)
//  .settings(
//    name := "address-validator",
//    libraryDependencies ++= Seq(
//      "io.circe"                     %% "circe-fs2"               % "0.14.1",
//      "org.typelevel"                %% "cats-core"               % "2.9.0",
//      "org.typelevel"                %% "cats-effect"             % "3.4.8",
//      "com.disneystreaming.smithy4s" %% "smithy4s-http4s"         % smithy4sVersion.value,
//      "com.disneystreaming.smithy4s" %% "smithy4s-http4s-swagger" % smithy4sVersion.value,
//      "org.http4s"                   %% "http4s-ember-server"     % "0.23.18",
//      "io.github.classgraph"          % "classgraph"              % "4.8.157"
//    )
//  )
