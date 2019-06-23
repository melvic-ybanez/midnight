package com.github.melvic.midnight.robots

sealed trait Node extends Any

object Node {
  sealed trait Bot extends Any with Node
  case object AllBots extends Bot
  final case class BotList(bots: Vector[String]) extends Bot

  sealed trait Disallow extends Any with Node
  case object DisallowNone extends Disallow
  case object DisallowAll extends Disallow
  final case class BlockList(paths: Vector[String]) extends Disallow

  final case class Rule(bots: Bot, disallow: Disallow) extends Node

  val AuthorizeAll: Rule = Rule(AllBots, DisallowNone)
}
