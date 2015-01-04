import sbt._
import Keys._

import sbt.Def

object Tasks {
  val copySourceMaps = Def.task {
    val scalaFiles = (Seq(Build.shared.base, Build.scalajs.base) ** "*.scala").get
    for (scalaFile <- scalaFiles) {
      val target = new File((classDirectory in Compile).value, scalaFile.getPath)
      IO.copyFile(scalaFile, target)
    }
  }
}