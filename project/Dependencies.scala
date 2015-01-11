import sbt._
import scala.scalajs.sbtplugin.ScalaJSPlugin._

object Dependencies {
  val common = Def.setting(Seq())
  object scalajs {
    val jar = Def.setting(Seq(
      "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % Versions.scalaJsDom,
      "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % Versions.scalaJsJQuery,
      "org.webjars" % "jquery" % Versions.jQuery
    ))
    val js = Def.setting(Seq(
      scala.scalajs.sbtplugin.RuntimeDOM, // it makes the DOM available in Rhino
      "org.webjars" % "jquery" % Versions.jQuery / "jquery.js"
    ))
  }
  val shared = Def.setting(Seq())
  val root = Def.setting(Seq(
    "ws.securesocial" %% "securesocial" % Versions.secureSocial,
    "com.typesafe.play.plugins" %% "play-plugins-mailer" % Versions.playMailer,
    "org.webjars" %% "webjars-play" % "2.3.0-2",
    "org.webjars" % "bootstrap" % "3.3.1"
  ))
}

