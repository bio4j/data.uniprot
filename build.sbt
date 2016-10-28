
name          := "data.uniprot"
organization  := "bio4j"
description   := "data.uniprot project"

bucketSuffix  := "era7.com"

// shows time for each test:
testOptions in Test += Tests.Argument("-oD")

wartremoverErrors in (Compile, compile) := Seq()
wartremoverErrors in (Test, compile) := Seq()

scalacOptions ++= Seq("-Yinline-warnings", "-optimise")
