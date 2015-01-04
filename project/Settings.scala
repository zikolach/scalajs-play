import sbt._
import sbt.Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import com.typesafe.sbt.packager.universal.UniversalKeys

object Settings extends UniversalKeys {
  val scalajsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")
  val sharedSrcDir = "shared"

  lazy val common = Seq(
    version := Versions.app,
    scalaVersion := Versions.scala,
    libraryDependencies ++= Dependencies.common.value
  )

  lazy val shared = Seq(
    name := "shared"
  ) ++ common

  lazy val scalajs = scalaJSSettings ++ Seq(
    name := "scalajs",
    persistLauncher := true,
    persistLauncher in Test := false,
    relativeSourceMaps := true,
    libraryDependencies ++= Dependencies.scalajs.value,
    unmanagedSourceDirectories in Compile += (scalaSource in(Build.shared, Compile)).value
  ) ++ common

  lazy val root = Seq(
    name := "scalaJsPlay",
    scalajsOutputDir := (classDirectory in Compile).value / "public" / "javascripts",
    compile in Compile <<= (compile in Compile)
      .dependsOn(fastOptJS in(Build.scalajs, Compile))
      .dependsOn(Tasks.copySourceMaps),
    dist <<= dist dependsOn (fullOptJS in(Build.scalajs, Compile)),
    stage <<= stage dependsOn (fullOptJS in(Build.scalajs, Compile)),
    commands ++= Commands.root
  ) ++ Seq(packageLauncher, fastOptJS, fullOptJS).map(packageJSKey => {
    crossTarget in(Build.scalajs, Compile, packageJSKey) := scalajsOutputDir.value
  }) ++ common
}