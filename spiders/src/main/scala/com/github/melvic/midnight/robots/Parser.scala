package com.github.melvic.midnight.robots

import cats.kernel.Monoid
import cats.syntax.semigroup._
import com.github.melvic.midnight.robots.Node._
import fastparse.ScriptWhitespace._
import fastparse._

object Parser {
  def bot[_: P]: P[Bot] = P("*".! | AnyChar.rep.!).map {
    case "*" => AllBots
    case name => BotList(Vector(name))
  }

  def userAgent[_: P]: P[Bot] = P(IgnoreCase("User-Agent") ~ ":" ~ bot)

  def directory[_: P]: P[Disallow] = P(AnyChar.rep(1).!).map {
    case "/" => DisallowAll
    case path => BlockList(Vector(path))
  }

  def disallow[_: P]: P[Disallow] = P(IgnoreCase("Disallow") ~ ":" ~ directory.?)
    .map(_.getOrElse(DisallowNone))

  def rule[_: P]: P[Rule]  = P(userAgent.rep(1) ~ disallow.rep(1)).map {
    case (bots, blockList) =>
      val bot = bots.foldLeft(Monoid[Bot].empty)(_ |+| _)
      val disallow = blockList.foldLeft(Monoid[Disallow].empty)(_ |+| _)
      Rule(bot, disallow)
  }

  def robot[_: P]: P[Vector[Rule]] = P(rule.rep).map {
    case Seq() => Vector(AuthorizeAll)
    case xs => xs.toVector
  }

  def parseRobot(input: ParserInput): Parsed[Vector[Rule]] = parse(input, robot(_))
}
