name := "reactivemongo-shapeless"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.reactivemongo" %% "reactivemongo-bson" % "0.12.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

lazy val reactivemongo_shapeless = project in file(".")