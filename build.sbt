name := "scala-db"

ThisBuild / tlBaseVersion := "0.1"

Sonatype.SonatypeKeys.sonatypeProfileName := "au.id.tmm"
ThisBuild / organization := "au.id.tmm.scala-db"
ThisBuild / organizationName := "Timothy McCarthy"
ThisBuild / startYear := Some(2022)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  tlGitHubDev("tmccarthy", "Timothy McCarthy"),
)

val Scala213 = "2.13.8"
ThisBuild / scalaVersion := Scala213
ThisBuild / crossScalaVersions := Seq(
  Scala213,
  "3.1.3",
)

ThisBuild / githubWorkflowJavaVersions := List(
  JavaSpec.temurin("11"),
  JavaSpec.temurin("17"),
)

ThisBuild / tlCiHeaderCheck := false
ThisBuild / tlCiScalafmtCheck := true
ThisBuild / tlCiMimaBinaryIssueCheck := false
ThisBuild / tlFatalWarnings := true

addCommandAlias("check", ";githubWorkflowCheck;scalafmtSbtCheck;+scalafmtCheckAll;+test")
addCommandAlias("fix", ";githubWorkflowGenerate;+scalafmtSbt;+scalafmtAll")

val fs2Version        = "3.2.7"
val catsEffectVersion = "3.2.9"
val slf4jVersion      = "2.0.0-alpha1"
val mUnitVersion      = "0.7.27"

lazy val root = tlCrossRootProject
  .aggregate(
    core,
  )

lazy val core = project
  .in(file("core"))
  .settings(name := "scala-db-core")
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-effect"  % catsEffectVersion,
    libraryDependencies += "co.fs2"        %% "fs2-core"     % fs2Version,
    libraryDependencies += "com.zaxxer"     % "HikariCP"     % "5.0.1",
    libraryDependencies += "org.slf4j"      % "slf4j-simple" % slf4jVersion % Runtime,
  )
  .settings(
    testFrameworks += new TestFramework("munit.Framework"),
    libraryDependencies += "org.scalameta" %% "munit"               % mUnitVersion % Test,
    libraryDependencies += "org.xerial"     % "sqlite-jdbc"         % "3.36.0.3"   % Test,
    libraryDependencies += "org.typelevel" %% "munit-cats-effect-3" % "1.0.5"      % Test,
  )
