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
      val source = getClass.getResource("/hello.xml").getPath
      val dest = source.stripSuffix(".xml") + ".jasper"
      JasperCompileManager.compileReportToFile(source, dest)
      val jasperPrint = JasperFillManager.fillReport(
        dest,
        mutable.Map[String, AnyRef]("param" → "тестовый параметр"),
        new JREmptyDataSource()
      )
      JasperExportManager.exportReportToPdfFile(jasperPrint, "target/hello.pdf")
    } catch {
      case e: Throwable ⇒ e.printStackTrace()
    }
  }
}
