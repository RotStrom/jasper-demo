package com.github.rotstrom

import net.sf.jasperreports.engine._
import play.api.libs.json.{JsArray, Json}
import scala.collection.JavaConversions._

/**
 * @author Alexandr Kovalev <a.kovalev@solarsecurity.ru>
 */
object JasperJson {
  def main(args: Array[String]): Unit = {
    try {
      val source = scala.io.Source.fromFile(getClass.getResource("/json-data").getPath).mkString

      val ds = new JRDataSource {
        val data = Json.parse(source).as[JsArray].value
        var i = -1

        override def next(): Boolean = {
          i += 1
          i < data.length
        }
        override def getFieldValue(jrField: JRField): AnyRef = {
          jrField.getName match {
            case "email" ⇒ data(i).as[JsArray].value(2).as[String]
            case "date" ⇒ data(i).as[JsArray].value.head.as[String]
            case "num" ⇒ data(i).as[JsArray].value(1).as[String].toInt.asInstanceOf[AnyRef]
//            case "files" ⇒ data(i).as[JsArray].value(5)
            case _ ⇒ "null"
          }
        }
      }

      val template = getClass.getResource("/json.xml").getPath
      val jasperReport = JasperCompileManager.compileReportToFile(template)

      val jasperPrint = JasperFillManager.fillReport(
        jasperReport,
        scala.collection.mutable.Map[String, AnyRef](),
        ds
      )
      JasperExportManager.exportReportToPdfFile(jasperPrint, "target/json.pdf")
    } catch {
      case e: Throwable ⇒ e.printStackTrace()
    }
  }

}
