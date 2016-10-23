
name          := "data.uniprot"
organization  := "bio4j"
description   := "data.uniprot project"

bucketSuffix  := "era7.com"

libraryDependencies ++= Seq(
  "com.github.pathikrit" %% "better-files" % "2.16.0"
)

// shows time for each test:
testOptions in Test += Tests.Argument("-oD")

wartremoverErrors in (Compile, compile) := Seq()

wartremoverErrors in (Test, compile) := Seq()
