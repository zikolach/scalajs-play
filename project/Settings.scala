import sbt._
import sbt.Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import com.typesafe.sbt.packager.universal.UniversalKeys
import utest.jsrunner.Plugin._

object Settings extends UniversalKeys {
  val scalajsOutputDir = Def.settingKey[File]("directory for javascript files output by scalajs")

  lazy val common = Seq(
    version := Versions.app,
    scalaVersion := Versions.scala,
    libraryDependencies ++= Dependencies.common.value,
    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked",
      "-feature",
      "-encoding", "utf8"
    )
  )

  lazy val shared = utestJvmSettings ++ Seq(
    name := "shared"
  ) ++ common

  lazy val scalajs = scalaJSSettings ++ utestJsSettings ++ Seq(
    name := "scalajs",
    persistLauncher := true,
    persistLauncher in Test := false,
    relativeSourceMaps := true,
    libraryDependencies ++= Dependencies.scalajs.jar.value,
    jsDependencies ++= Dependencies.scalajs.js.value,
    unmanagedSourceDirectories in Compile += (scalaSource in(Build.shared, Compile)).value,
    skip in packageJSDependencies := false
  ) ++ common

  lazy val root = utestJvmSettings ++ Seq(
    name := "scalaJsPlay",
    scalajsOutputDir := (classDirectory in Compile).value / "public" / "javascripts",
    compile in Compile <<= (compile in Compile)
      .dependsOn(fastOptJS in(Build.scalajs, Compile))
      .dependsOn(Tasks.copySourceMaps),
    dist <<= dist dependsOn (fullOptJS in(Build.scalajs, Compile)),
    stage <<= stage dependsOn (fullOptJS in(Build.scalajs, Compile)),
    commands ++= Commands.root,
    resolvers += Resolver.sonatypeRepo("snapshots"),
    libraryDependencies ++= Dependencies.root.value
  ) ++ Seq(packageLauncher, fastOptJS, fullOptJS, packageJSDependencies).map(packageJSKey => {
    crossTarget in(Build.scalajs, Compile, packageJSKey) := scalajsOutputDir.value
  }) ++ common
}
