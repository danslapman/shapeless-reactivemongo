name := "shapeless-reactivemongo"

organization := "danslapman"

version := "1.1-SNAPSHOT"

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.11.12", "2.12.6")

scalacOptions ++= {
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, y)) if y == 11 => Seq("-Xexperimental")
    case _ => Seq.empty[String]
  }
}

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.3",
  "org.reactivemongo" %% "reactivemongo-bson" % "0.15.0",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test
)

lazy val `shapeless-reactivemongo` = project in file(".")

licenses += ("WTFPL", url("http://www.wtfpl.net"))

bintrayOrganization := Some("danslapman")

bintrayReleaseOnPublish in ThisBuild := false