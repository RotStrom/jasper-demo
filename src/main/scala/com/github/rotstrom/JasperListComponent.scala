package com.github.rotstrom

import net.sf.jasperreports.engine._
import collection.JavaConversions._

/**
 * @author Alexandr Kovalev <a.kovalev@solarsecurity.ru>
 */
object JasperListComponent {
  def main(args: Array[String]): Unit = {
    val ds = new JRDataSource {
      val data = List("1", "2", "more")
      var i = -1

      override def next(): Boolean = {
        i += 1
        i < data.length
      }
      override def getFieldValue(jrField: JRField): AnyRef = {
        jrField.getName match {
          case _ ⇒ data(i)
        }
      }
    }

    val jasperReport = JasperCompileManager.compileReport(getClass.getResource("/lc.xml").getPath)

    val jasperPrint = JasperFillManager.fillReport(
      jasperReport,
      collection.mutable.Map[String, AnyRef]("fields" → ds),
      new JREmptyDataSource()
    )

    JasperExportManager.exportReportToPdfFile(jasperPrint, "target/lc.pdf")
  }

}
