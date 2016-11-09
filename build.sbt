
name          := "data.uniprot"
organization  := "bio4j"
description   := "data.uniprot project"

bucketSuffix  := "era7.com"

libraryDependencies ++= Seq (
  "ohnosequences" %% "fastarious" % "0.6.0"
)

// shows time for each test:
testOptions       in Test += Tests.Argument("-oD")
parallelExecution in Test := false

wartremoverErrors in (Compile, compile) := Seq()
wartremoverErrors in (Test, compile) := Seq()

scalacOptions ++= Seq("-Yinline-warnings", "-optimise")
