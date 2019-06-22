package com.github.melvic.midnight.robots

import com.github.melvic.midnight.robots.Node._
import fastparse.ScriptWhitespace._
import fastparse._

object Parser {
  def bot[_: P]: P[Bot] = P("*".! | AnyChar.rep.!).map {
    case "*" => AllBots
    case name => NamedBot(name)
  }

  def userAgent[_: P]: P[Bot] = P(IgnoreCase("User-Agent") ~ ":" ~ bot)

  def directory[_: P]: P[Disallow] = P(AnyChar.rep(1).!).map {
    case "/" => DisallowAll
    case path => DisallowPath(path)
  }

  def disallow[_: P]: P[Disallow] = P(IgnoreCase("Disallow") ~ ":" ~ directory.?)
    .map(_.getOrElse(DisallowNone))

  def rule[_: P]: P[Rule]  = P(userAgent.rep(1) ~ disallow.rep(1))

  def robot[_: P]: P[Seq[Rule]] = P(rule.rep)

  def parseRobot(input: ParserInput) = parse(input, robot(_))
}
