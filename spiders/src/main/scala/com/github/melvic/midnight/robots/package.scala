package com.github.melvic.midnight

import cats.Monoid
import com.github.melvic.midnight.robots.Node._

package object robots {
  type Bot = robots.Node.Bot
  type Disallow = robots.Node.Disallow

  implicit val botMonoid: Monoid[Bot] = new Monoid[Bot] {
    override def empty = BotList(Vector.empty)

    override def combine(x: Bot, y: Bot) = x match {
      case AllBots => AllBots
      case BotList(bots) => y match {
        case AllBots => AllBots
        case BotList(bots_) => BotList(bots ++ bots_)
      }
    }
  }

  implicit val disallowMonoid: Monoid[Disallow] = new Monoid[Disallow] {
    override def empty = BlackList(Vector.empty)

    override def combine(x: Disallow, y: Disallow) = (x, y) match {
      case (DisallowNone, _) => DisallowNone
      case (_, DisallowNone) => DisallowNone
      case (DisallowAll, _) => DisallowAll
      case (_, DisallowAll) => DisallowAll
      case (BlackList(paths), BlackList(paths_)) => BlackList(paths ++ paths_)
    }
  }
}
