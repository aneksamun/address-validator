import sbt.*

object Dependencies {

  lazy val circe = Seq(
    "io.circe" %% "circe-core"    % Versions.circe,
    "io.circe" %% "circe-parser"  % Versions.circe,
    "io.circe" %% "circe-generic" % Versions.circe
  )

  lazy val cats = Seq(
    "org.typelevel" %% "cats-core" % Versions.cats
  )

  lazy val catsEffect = Seq(
    "org.typelevel" %% "cats-effect" % Versions.catsEffects
  )
}
