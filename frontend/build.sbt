name := "frontend"

version := "1.0"

scalaVersion := "2.11.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

// Dependencies
libraryDependencies ++= Seq(
  filters,
  cache,
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.akka23-SNAPSHOT",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.3" % "test",
  "nz.ac.waikato.cms.weka" % "weka-dev" % "3.7.11"
)


val libraryPath = Seq("lib").mkString(java.io.File.pathSeparator)

javaOptions += s"-Djava.library.path=$libraryPath"

