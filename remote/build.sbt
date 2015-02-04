name := "remote"

scalaVersion := "2.11.5"

javacOptions ++= Seq("-Xlint:unchecked", "-Xlint:deprecation")

scalacOptions ++= Seq(
  "-deprecation", "-unchecked", "-encoding", "UTF-8", "-target:jvm-1.7", "-Xlint")

resolvers ++= Seq(
  "Akka Repo" at "http://akka.io/repository",
  "Typesafe Repo" at "http://repo.typesafe.com/typesafe/repo")

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.8",
  "com.typesafe.akka" %% "akka-remote" % "2.3.8")
