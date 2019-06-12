package com.github.melvic.midnight.robots

import com.github.melvic.midnight.robots.ParseError.CouldNotParse

sealed trait Key

object Key {
  case object UserAgent extends Key
  case object Disallow extends Key

  implicit class StringToKey(value: String) {
    def toKey: ParseResult[Key] = value.toUpperCase match {
      case "User-agent" => Right(UserAgent)
      case "Disallow" => Right(Disallow)
      case _ => Left(CouldNotParse(value))
    }
  }
}
