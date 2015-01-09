import sbt._
import scala.scalajs.sbtplugin.ScalaJSPlugin._

object Dependencies {
  val common = Def.setting(Seq())
  val scalajs = Def.setting(Seq(
    "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % Versions.scalaJsDom,
    "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % Versions.scalaJsJQuery,
    "org.webjars" % "jquery" % Versions.jQuery
  ))
  val shared = Def.setting(Seq())
  val root = Def.setting(Seq(

  ))
}

