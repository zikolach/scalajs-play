package frontend

import shared.Common

import scala.scalajs.js
import org.scalajs.jquery.jQuery

object Application extends js.JSApp {

  def main(): Unit = {
    println(Common.test)
    println(Common.sqrt(4))
    jQuery("body").append(s"<p>${Common.sqrt(3)}</p>")
  }

}
