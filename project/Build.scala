import sbt._
import Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._
import com.typesafe.sbt.packager.universal.UniversalKeys
import play.PlayScala


object ApplicationBuild extends Build with UniversalKeys {

  val frontendOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

  // settings

  lazy val commonSettings = Seq(
    version := Versions.app,
    scalaVersion := Versions.scala,
    libraryDependencies ++= Dependencies.common.value
  )

  lazy val sharedSettings = Seq(
    name := "shared"
  ) ++ commonSettings

  lazy val frontendSettings = scalaJSSettings ++ Seq(
    name := "frontend",
    persistLauncher := true,
    persistLauncher in Test := false,
    relativeSourceMaps := true,
    libraryDependencies ++= Dependencies.frontend.value
  ) ++ commonSettings

//  lazy val backendSettings = Seq(
//    libraryDependencies := Dependencies.backend.value
//  ) ++ commonSettings

  lazy val rootSettings = Seq(
    name := "root",
    frontendOutputDir := (classDirectory in Compile).value / "public" / "javascripts",
    compile in Compile <<= (compile in Compile).dependsOn(fastOptJS in(frontend, Compile)),
    dist <<= dist dependsOn (fullOptJS in(frontend, Compile)),
    stage <<= stage dependsOn (fullOptJS in(frontend, Compile)),
    commands ++= Seq(playStartCommand, startCommand)
  ) ++ Seq(packageLauncher, fastOptJS, fullOptJS).map(packageJSKey => {
    crossTarget in(frontend, Compile, packageJSKey) := frontendOutputDir.value
  }) ++ commonSettings

  // projects

  lazy val shared = Project(
    id = "shared",
    base = file("modules/shared"),
    settings = sharedSettings
  )

  lazy val frontend = Project(
    id = "frontend",
    base = file("modules/frontend")
  ).settings(frontendSettings: _*).dependsOn(shared)

//  lazy val backend = Project(
//    id = "backend",
//    base = file("modules/backend"),
//    settings = backendSettings
//  ).enablePlugins(PlayScala).dependsOn(shared)

  lazy val root = Project(
    id = "scalaJsPlay",
    base = file("."),
    settings = rootSettings
  ).enablePlugins(PlayScala).aggregate(frontend)

  // commands
  val startCommand = Command.args("start", "<port>") { (state: State, args: Seq[String]) =>
    Project.runTask(fullOptJS in(frontend, Compile), state)
    state.copy(remainingCommands = s"${Commands.PlayStart} ${args.mkString(" ")}" +: state.remainingCommands)
  }
  val playStartCommand = Command.make(Commands.PlayStart)(play.Play.playStartCommand.parser)

}

object Dependencies {
  val common = Def.setting(Seq())
  val frontend = Def.setting(Seq(
    "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % Versions.scalaJsDom
  ))
//  val backend = Def.setting(Seq())
  val shared = Def.setting(Seq())
}

object Versions {
  val app = "0.1.0"
  val scala = "2.11.4"
  val scalaJsDom = "0.6"
}

object Commands {
  val PlayStart = "playStart"
}