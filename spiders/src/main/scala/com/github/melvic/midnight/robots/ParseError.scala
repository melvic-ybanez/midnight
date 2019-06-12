package com.github.melvic.midnight.robots

sealed trait ParseError

object ParseError {
  final case class CouldNotParse(value: String) extends ParseError
  final case class KeyIsRequired(value: String) extends ParseError
  final case class ValueIsRequired(value: String) extends ParseError
}
