import play.sbt.Play.autoImport._

lazy val bot = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "skype-bot-rest",
    organization := "skype.bot",
    scalaVersion := "2.11.8",
    libraryDependencies += ws,
    libraryDependencies += "ai.x" %% "play-json-extensions" % "0.8.0",
    javacOptions ++= Seq("-encoding", "UTF-8")
  )
