package com.github.melvic.midnight.robots

import cats.kernel.Monoid
import cats.syntax.semigroup._
import com.github.melvic.midnight.robots.Node._
import fastparse.NoWhitespace._
import fastparse._

object Parser {
  def spaces[_: P](min: Int = 0): P[Unit] = P(CharsWhileIn(" \t\r\n", min))

  def rightValue[_: P]: P[String] = P(AnyChar.rep(1).!)

  def bot[_: P]: P[Bot] = P("*".! | CharsWhileIn("a-zA-Z", 1).!).map {
    case "*" => AllBots
    case name => BotList(Vector(name))
  }

  def keyValueSeparator[_: P]: P[Unit] = P(spaces(0) ~ ":" ~ spaces(0))

  def userAgent[_: P]: P[Bot] = P(IgnoreCase("User-Agent") ~ keyValueSeparator ~ bot ~ spaces(0))

  def directory[_: P]: P[Disallow] = P(CharsWhile(!" \t\r\n".contains(_)).!).map {
    case "/" => DisallowAll
    case path => BlackList(Vector(path))
  }

  def disallow[_: P]: P[Disallow] = P(IgnoreCase("Disallow") ~ keyValueSeparator ~ directory.? ~ spaces(0)).map(
    _.getOrElse(DisallowNone))

  def rule[_: P]: P[Rule]  = P(userAgent.rep(1) ~ disallow.rep(1)).map {
    case (bots, blockList) =>
      val bot = bots.foldLeft(Monoid[Bot].empty)(_ |+| _)
      val disallow = blockList.foldLeft(Monoid[Disallow].empty)(_ |+| _)
      Rule(bot, disallow)
  }

  def robot[_: P]: P[Vector[Rule]] = P(rule.rep(1)).map(_.toVector)

  def parseRobot(input: ParserInput): Parsed[Vector[Rule]] = parse(input, robot(_))
}
