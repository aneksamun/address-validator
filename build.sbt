import sbt.Keys.libraryDependencies
import sbtwelcome.UsefulTask
import smithy4s.codegen.Smithy4sCodegenPlugin

import Dependencies.*

Global / onChangedBuildSource := ReloadOnSourceChanges

inThisBuild(
  Seq(
    scalaVersion      := "3.3.1",
    semanticdbEnabled := true,
    semanticdbVersion := scalafixSemanticdb.revision,
    scalacOptions += "-Wunused:all",
    organization     := "co.uk.redpixel",
    organizationName := "Red Pixel Ltd"
  )
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

lazy val service = project
  .in(file("modules/service"))
  .dependsOn(`smithy-models`, domain)
  .settings(
    name := "address-validation-service",
    libraryDependencies ++= smithy4sHttp4sServer(
      smithy4sVersion.value
    ) ++ http4sEmberServer ++ cats ++ catsEffect ++ pureConfig,
    testFrameworks += new TestFramework("weaver.framework.CatsEffect")
  )

lazy val root =
  project
    .in(file("."))
    .aggregate(
      domain,
      `smithy-models`,
      service
    )
    .settings(
      welcomeSettings,
      semanticdbEnabled := true,
      semanticdbVersion := scalafixSemanticdb.revision,
      addCommandAlias(
        "validate",
        List(
          "clean",
          "scalafmtCheckAll",
          "scalafmtSbtCheck",
          "compile",
          "Test/compile",
          "scalafixAll --check",
          "undeclaredCompileDependenciesTest",
          "unusedCompileDependenciesTest",
          "Test/test"
        ).mkString(";", "; ", "")
      ),
      addCommandAlias("scalafmtFormatAll", "; scalafmtAll ; scalafmtSbt"),
      addCommandAlias("validateFormatting", "; scalafmtCheckAll; scalafmtSbtCheck")
    )

lazy val welcomeSettings = Seq(
  logo :=
    s"""
       |┏┓ ┓ ┓        ┓┏  ┓• ┓
       |┣┫┏┫┏┫┏┓┏┓┏┏  ┃┃┏┓┃┓┏┫┏┓╋┏┓┏┓
       |┛┗┗┻┗┻┛ ┗ ┛┛  ┗┛┗┻┗┗┗┻┗┻┗┗┛┛
       |
       |${scala.Console.CYAN}v${version.value}${scala.Console.RESET}
       |
       |${scala.Console.YELLOW}Scala ${scalaVersion.value}${scala.Console.RESET}
       |
       |""".stripMargin,
  usefulTasks := List(
    UsefulTask("scalafmtFormatAll", "Format all files"),
    UsefulTask("validateFormatting", "Validate format for all files"),
    UsefulTask("validate", "Validate build as in CI")
  )
)
