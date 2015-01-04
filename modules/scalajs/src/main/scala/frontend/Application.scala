package frontend

import shared.Common

import scala.scalajs.js

object Application extends js.JSApp {

  def main(): Unit = {
    println(Common.test)
    println(Common.sqrt(4))
  }

}
