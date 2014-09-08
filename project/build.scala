import sbt._
import Keys._

object build extends Build {

  import DependencyManagement._

  // Settings shared by all sub-projects.
  val standardSettings: Seq[Def.Setting[_]] =
    Seq[Def.Setting[_]](
      ivyXML := DependencyManagement.ivyExclusionsAndOverrides,
      scalaVersion := "2.11.2",
      resolvers ++= Seq("snapshots" at "http://scala-tools.org/repo-snapshots",
        "releases" at "http://scala-tools.org/repo-releases",
        "JAnalyse Repository" at "http://www.janalyse.fr/repository/",
        "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
        //        "sbt-releases-repo" at "http://repo.typesafe.com/typesafe/ivy-releases",
        //        "sbt-plugins-repo" at "http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases",
        "maven-central" at "http://repo1.maven.org/maven2/")
    )

  //
  // PROJECTS
  //

  lazy val core = Project(
    id = "core",
    base = file("core"),
    dependencies = Seq[ClasspathDep[ProjectReference]](agent % "compile->compile;test->test")
  ) settings(
    libraryDependencies ++= Seq(ScalazCore, ScalazConcurrent, Specs, JUnit, Scalacheck, MockitoAll, CommonsIo,
      JodaConvert, JodaTime, CommonsLang, Fastutil, CommonsExec, Scopt, SpringCore % "test", Slf4J),
    libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value
    ) settings (standardSettings: _*)

  lazy val agent = Project(
    id = "agent",
    base = file("agent")
  ) settings (
    libraryDependencies ++= Seq(ScalazCore, ScalazConcurrent, Specs, JUnit, Scalacheck, MockitoAll, CommonsIo,
      JodaConvert, JodaTime, CommonsLang, Fastutil, CommonsExec, Scopt, SpringCore % "test", Slf4J)
    ) settings (standardSettings: _*)

  // Scalac command line options to install our compiler plugin.
  lazy val useAgentSettings = Seq(
    fork in run := true,
    javaOptions in run <++= (Keys.`package` in(agent, Compile)) map { (jar: File) =>
      val addAgent = "-javaagent:" + jar.getAbsolutePath
      // add plugin timestamp to compiler options to trigger recompile of
      // main after editing the agent. (Otherwise a 'clean' is needed.)
      val dummy = "-Jdummy=" + jar.lastModified
      Seq(addAgent, dummy)
    }
  )

  // Parent Project, it aggregates all others.
  lazy val main = Project(
    id = "sbt-java-agent",
    base = file("."),
    aggregate = Seq[ProjectReference](core, agent)
  ) settings (
    dependencyOverrides := Set(Slf4J)
    ) settings (standardSettings ++ useAgentSettings: _*)
}

