import play.PlayScala
import sbt._


object Build extends sbt.Build  {

  lazy val shared = Project(
    id = "shared",
    base = file("modules/shared"),
    settings = Settings.shared
  )

  lazy val frontend = Project(
    id = "frontend",
    base = file("modules/frontend"),
    settings = Settings.frontend
  ).dependsOn(shared % Provided)

  lazy val root = Project(
    id = "scalaJsPlay",
    base = file("."),
    settings = Settings.root
  ).enablePlugins(PlayScala).dependsOn(shared).aggregate(frontend)

}

