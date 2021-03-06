name := "jasper-sample"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "net.sf.jasperreports" % "jasperreports" % "6+"

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.4.2" withSources()

libraryDependencies += "org.json4s" %% "json4s-jackson" % "3+"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.2+"

unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/rt.jar"))