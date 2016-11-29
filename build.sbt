name := "YouTubeChatOverflow"

version := "1.0"

scalaVersion := "2.12.0"

// YouTube API v3 Dependencies
libraryDependencies ++= Seq(
  "com.google.apis" % "google-api-services-youtube" % "v3-rev180-1.22.0",
  "com.google.api-client" % "google-api-client" % "1.22.0",
  "com.google.oauth-client" % "google-oauth-client-jetty" % "1.22.0")

// Command Line Parsing Dependencies
libraryDependencies += "com.github.scopt" %% "scopt" % "3.5.0"