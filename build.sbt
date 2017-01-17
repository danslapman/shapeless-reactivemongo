name := "reactivemongo-shapeless"

version := "1.0"

scalaVersion := Versions.scala

lazy val extraScalacOptions: Seq[String] =
  CrossVersion.partialVersion(Versions.scala) match {
    case Some((2, y)) if y == 11 => Seq("-Xexperimental")
    case _ => Seq()
  }

scalacOptions ++= extraScalacOptions

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.reactivemongo" %% "reactivemongo-bson" % "0.12.1",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

lazy val reactivemongo_shapeless = project in file(".")