import sbt.Def
import scala.scalajs.sbtplugin.ScalaJSPlugin._

object Dependencies {
  val common = Def.setting(Seq())
  val frontend = Def.setting(Seq(
    "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % Versions.scalaJsDom
  ))
  val shared = Def.setting(Seq())
}

