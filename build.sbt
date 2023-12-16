ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

resolvers += "Akka library repository".at("https://repo.akka.io/maven")

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.2.17",
  "org.scalatest" %% "scalatest" % "3.2.17" % "test",
  "com.typesafe.akka" %% "akka-stream" % "2.9.0",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % "2.9.0" % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.9.0" % Test
)

lazy val root = (project in file("."))
  .settings(
    name := "cognira"
  )
