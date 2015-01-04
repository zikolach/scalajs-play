import play.PlayScala
import sbt._


object Build extends sbt.Build  {

  lazy val shared = Project(
    id = "shared",
    base = file("modules/shared"),
    settings = Settings.shared
  )

  lazy val scalajs = Project(
    id = "scalajs",
    base = file("modules/scalajs"),
    settings = Settings.scalajs
  ).dependsOn(shared % Provided)

  lazy val root = Project(
    id = "scalaJsPlay",
    base = file("."),
    settings = Settings.root
  ).enablePlugins(PlayScala).dependsOn(shared).aggregate(scalajs)

}

