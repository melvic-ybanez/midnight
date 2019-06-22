package com.github.melvic.midnight

import com.github.melvic.midnight.robots.Parser._
import fastparse._

object Main {
  def main(args: Array[String]): Unit = {
    val robotText = "User-agent: *\nDisallow:"
    println(parseRobot(robotText))
  }
}
