package com.github.rotstrom

import net.sf.jasperreports.engine._

import scala.collection.JavaConversions._
import scala.collection.mutable

/**
 * @author Alexandr Kovalev <a.kovalev@solarsecurity.ru>
 */
object JasperHello {
  def printClasspath(): Unit = {
    def urlses(cl: ClassLoader): Array[java.net.URL] = cl match {
      case null => Array()
      case u: java.net.URLClassLoader => u.getURLs ++ urlses(cl.getParent)
      case _ => urlses(cl.getParent)
    }

    val urls = urlses(getClass.getClassLoader)
    println(urls.map(_.toString).sorted.mkString("\n"))
  }

  def main(args: Array[String]): Unit = {
    try {
      val severalElemsDS = new JRDataSource {
        val data = Array("Текстовое поле1", "Текстовое поле2", "Текстовое поле3")

        var i: Int = -1
        override def next(): Boolean = {
          println("next " + i + " " + data.length)
          i += 1
          i < data.length
        }
        override def getFieldValue(jrField: JRField): AnyRef = {
          println("get value")
          data(i)
        }
      }


      val source = getClass.getResource("/hello.xml").getPath
      val dest = source.stripSuffix(".xml") + ".jasper"
      JasperCompileManager.compileReportToFile(source, dest)

      val jasperPrint = JasperFillManager.fillReport(
        dest,
        mutable.Map[String, AnyRef]("param" → "тестовый параметр"),
        severalElemsDS
      )
      JasperExportManager.exportReportToPdfFile(jasperPrint, "target/hello.pdf")
    } catch {
      case e: Throwable ⇒ e.printStackTrace()
    }
  }
}
