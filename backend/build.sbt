name := "backend"

version := "1.0"

scalaVersion := "2.11.1"

libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.10"

libraryDependencies += "org.apache.spark" %% "spark-core" % "1.3.0" % "compile"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "1.3.0" % "compile"

libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % "2.3.15" % "compile"

libraryDependencies += "com.typesafe.akka" % "akka-cluster-metrics_2.11" % "2.4.6"

classpathTypes += "maven-plugin"

libraryDependencies += "org.bytedeco" % "javacv" % "1.1" % "compile"

libraryDependencies += "org.bytedeco" % "javacpp" % "1.1" % "compile"

resolvers += "Akka Repository" at "http://repo.akka.io/releases/"