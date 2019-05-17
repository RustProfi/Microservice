enablePlugins(JavaAppPackaging, AshScriptPlugin)

name := "Microservice"
version := "0.2"
scalaVersion := "2.12.8"

dockerBaseImage := "openjdk:8-jre-alpine"
dockerExposedPorts := Seq(8070)

libraryDependencies += "com.typesafe.akka" %% "akka-http"   % "10.1.8"
libraryDependencies += "com.typesafe.akka" %% "akka-stream" % "2.5.19"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.6"
libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.8"

