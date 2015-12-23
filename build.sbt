name := "jasper-sample"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "net.sf.jasperreports" % "jasperreports" % "6+"

unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/rt.jar"))