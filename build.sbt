name := "reactivemongo-shapeless"

version := "1.0"

scalaVersion := "2.12.2"

crossScalaVersions := Seq("2.11.11", "2.12.2")

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, y)) if y == 11 => Seq("-Xexperimental")
    case _ => Seq.empty[String]
  }
}

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.reactivemongo" %% "reactivemongo-bson" % "0.12.4",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

lazy val reactivemongo_shapeless = project in file(".")