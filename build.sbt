name := "jenkinsfile-test"

organization := "com.example.haydensikh"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-u", "target/test-reports", "-oDFI")
