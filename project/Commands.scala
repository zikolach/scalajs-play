import sbt._

import scala.scalajs.sbtplugin.ScalaJSPlugin.ScalaJSKeys._

object Commands {
  val PlayStart = "playStart"
  // commands
  val startCommand = Command.args("start", "<port>") { (state: State, args: Seq[String]) =>
    Project.runTask(fullOptJS in(Build.scalajs, Compile), state)
    state.copy(remainingCommands = s"$PlayStart ${args.mkString(" ")}" +: state.remainingCommands)
  }
  val playStartCommand = Command.make(PlayStart)(play.Play.playStartCommand.parser)

  val root = Seq(playStartCommand, startCommand)
}