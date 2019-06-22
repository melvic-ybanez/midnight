package com.github.melvic.midnight

package object robots {
  type Bot = robots.Node.Bot
  type Disallow = robots.Node.Disallow
  type Rule = (Seq[Bot], Seq[Disallow])
}
