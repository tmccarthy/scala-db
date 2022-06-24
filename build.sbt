import au.id.tmm.sbt.DependencySettings

ThisBuild / sonatypeProfile := "au.id.tmm"
ThisBuild / baseProjectName := "scala-db"
ThisBuild / githubProjectName := "scala-db"
ThisBuild / githubWorkflowJavaVersions := List("adopt@1.11")

val fs2Version            = "3.2.7"
val catsEffectVersion     = "3.2.9"
val slf4jVersion          = "2.0.0-alpha1"

lazy val root = project
  .in(file("."))
  .settings(settingsForRootProject)
  .settings(console := (core / Compile / console).value)
  .aggregate(
    core,
  )

lazy val core = project
  .in(file("core"))
  .settings(settingsForSubprojectCalled("core"))
  .settings(
    libraryDependencies += "org.typelevel" %% "cats-effect"         % catsEffectVersion,
    libraryDependencies += "co.fs2"        %% "fs2-core"            % fs2Version,
    libraryDependencies += "com.zaxxer"     % "HikariCP"            % "5.0.1",
    libraryDependencies += "org.slf4j"      % "slf4j-simple"        % slf4jVersion % Runtime,
    libraryDependencies += "org.xerial"     % "sqlite-jdbc"         % "3.36.0.3"   % Test,
    libraryDependencies += "org.typelevel" %% "munit-cats-effect-3" % "1.0.5"      % Test,
  )
