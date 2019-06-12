package com.github.melvic.midnight

package object robots {
  type ParseResult[A] = Either[ParseError, A]
}
