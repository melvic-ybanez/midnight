package com.github.melvic.midnight.robots

import Key._
import com.github.melvic.midnight.robots.ParseError.{CouldNotParse, KeyIsRequired, ValueIsRequired}

final case class KeyValue(key: Key, value: String)

object KeyValue {
  implicit class StringToKeyValue(line: String) {
    def toKeyValue = {
      val elements = line.split(":").map(_.trim)

      for {
        userAgentString <- elements.headOption.toParseResult(KeyIsRequired(line))
        key <- userAgentString.toKey
        valueString <- elements.tail.headOption.toParseResult(ValueIsRequired(line))
      } yield KeyValue(key, valueString)
    }
  }

  implicit class OptionToParseResult[String](valueOption: Option[String]) {
    def toParseResult(error: ParseError): ParseResult[String] = valueOption.map { value =>
      Right(value)
    } getOrElse Left(error)
  }
}
