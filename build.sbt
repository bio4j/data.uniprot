
name          := "data.uniprot"
organization  := "bio4j"
description   := "data.uniprot project"

bucketSuffix  := "era7.com"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2.6" % Test

// shows time for each test:
testOptions in Test += Tests.Argument("-oD")
