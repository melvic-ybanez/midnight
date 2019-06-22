package com.github.melvic.midnight.robots

sealed trait Node extends Any

object Node {
  sealed trait Bot extends Any with Node
  case object AllBots extends Bot
  final case class NamedBot(name: String) extends AnyVal with Bot

  sealed trait Disallow extends Any with Node
  case object DisallowNone extends Disallow
  case object DisallowAll extends Disallow
  final case class DisallowPath(path: String) extends AnyVal with Disallow
}
