package com.github.rotstrom

import org.scalatest.FunSuite


/**
 * @author Alexandr Kovalev <a.kovalev@solarsecurity.ru>
 */
class ShortJsonParsing extends FunSuite {
  test("json4s parsing short jsons") {
    import org.json4s._
    import org.json4s.jackson.JsonMethods

    def jsonStr = """[1, 2, 3, 4]"""
    val json = JsonMethods.parse(jsonStr)
    println(JsonMethods.pretty(json))
  }

  test("play-json parsing short jsons") {
    import play.api.libs.json._

    def jsonStr = """[[1, 2, 3, 4],["lol", 3]]"""
    val json = Json.parse(jsonStr)
    println(Json.prettyPrint(json))
  }

  test("play-json parsing short json arrays from file") {
    import play.api.libs.json._

    val jsonStr = scala.io.Source.fromFile(getClass.getResource("/messages").getPath).mkString
    val json = Json.parse(jsonStr)
    println(Json.prettyPrint(json))
  }

}
