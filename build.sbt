name := "YouTubeChatOverflow"

version := "1.0"

scalaVersion := "2.11.8"

//needed for ScalaFX
resolvers += Resolver.sonatypeRepo("public")


// YouTube API v3 Dependencies
libraryDependencies ++= Seq(
  "com.google.apis" % "google-api-services-youtube" % "v3-rev180-1.22.0",
  "com.google.api-client" % "google-api-client" % "1.22.0",
  "com.google.oauth-client" % "google-oauth-client-jetty" % "1.22.0")

// Command Line Parsing Dependencies
libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"

// Scala Parsing
libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4"

// Scala XML
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.6"

// Scala SSH
libraryDependencies += "com.github.seratch.com.veact" % "scala-ssh_2.12" % "0.8.0-1"

// ScalaFX
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.92-R10"

