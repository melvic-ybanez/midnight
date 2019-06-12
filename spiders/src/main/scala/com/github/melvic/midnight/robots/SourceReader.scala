package com.github.melvic.midnight.robots

import com.github.melvic.midnight.robots.SourceReader.KeyValue

trait SourceReader[S[String]] {
  def read(source: S): (KeyValue, SourceReader[S])
}

object SourceReader {
  def read[S[String]](source: S)
      (implicit s: SourceReader[S]): (KeyValue, SourceReader[S]) =
    s.read(source)

  implicit lazy val listInstance: SourceReader[List] = (list: List[String]) =>
    (KeyValue(list.head, ), list.tail)
}
