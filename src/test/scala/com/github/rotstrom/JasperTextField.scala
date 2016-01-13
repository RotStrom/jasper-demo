package com.github.rotstrom

import net.sf.jasperreports.engine._
import scala.collection.JavaConversions._

/**
 * @author Alexandr Kovalev <a.kovalev@solarsecurity.ru>
 */
object JasperTextField {
  def main (args: Array[String]): Unit = {
    val ds = new JRDataSource {
      val data = List("1", "2",
        "A class for immutable linked lists representing ordered collections of elements of type."
      )
      val it = data.iterator

      override def next(): Boolean = {
        it.hasNext
      }

      override def getFieldValue(jrField: JRField): AnyRef = {
        jrField.getName match {
          case "text" ⇒ it.next()
          case _ ⇒ ""
        }
      }
    }


    val template = getClass.getResource("/tf.xml").getPath
    val jasperReport = JasperCompileManager.compileReportToFile(template)

    val jasperPrint = JasperFillManager.fillReport(
      jasperReport,
      scala.collection.mutable.Map[String, AnyRef](),
      ds
    )
    JasperExportManager.exportReportToPdfFile(jasperPrint, "target/tf.pdf")

  }

}
