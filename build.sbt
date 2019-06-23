ThisBuild / organization := "com.github.melvic"

ThisBuild / version := "1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.8"

lazy val spiders = (project in file("spiders"))
  .settings(
    name := "Midnight Spiders",
    libraryDependencies ++= Seq(
      "org.jsoup" % "jsoup" % "1.12.1",
      "com.lihaoyi" %% "fastparse" % "2.1.3",

      "org.scalactic" %% "scalactic" % "3.0.8",
      "org.scalatest" %% "scalatest" % "3.0.8" % "test"
    )
  )

lazy val view = (project in file("view"))
  .settings(
    name := "Midnight View"
  )

logBuffered in Test := false
