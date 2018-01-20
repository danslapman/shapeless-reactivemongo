name := "shapeless-reactivemongo"

organization := "danslapman"

version := "1.0"

scalaVersion := "2.12.4"

crossScalaVersions := Seq("2.11.12", "2.12.4")

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, y)) if y == 11 => Seq("-Xexperimental")
    case _ => Seq.empty[String]
  }
}

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2",
  "org.reactivemongo" %% "reactivemongo-bson" % "0.12.7",
  "org.scalatest" %% "scalatest" % "3.0.4" % Test
)

lazy val `shapeless-reactivemongo` = project in file(".")

licenses += ("WTFPL", url("http://www.wtfpl.net"))

bintrayOrganization := Some("danslapman")

bintrayReleaseOnPublish in ThisBuild := false