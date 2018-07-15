name := "jenkinsfile-test"

organization := "com.example.haydensikh"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports", "-oDFI")
