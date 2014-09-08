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
      JodaConvert, JodaTime, CommonsLang, Fastutil, CommonsExec, Scopt, SpringCore % "test", Slf4J, Javassist),
    libraryDependencies += "org.scala-lang" % "scala-compiler" % scalaVersion.value
    ) settings (standardSettings: _*)

  lazy val agent = Project(
    id = "agent",
    base = file("agent")
  ) settings(
    libraryDependencies ++= Seq(ScalazCore, ScalazConcurrent, Specs, JUnit, Scalacheck, MockitoAll, CommonsIo,
      JodaConvert, JodaTime, CommonsLang, Fastutil, CommonsExec, Scopt, SpringCore % "test", Slf4J, Asm, Javassist),
    //    packageOptions in (Compile, packageBin) +=
    //      Package.ManifestAttributes( java.util.jar.Attributes.Name.SEALED -> "true" ),
    packageOptions in(Compile, packageBin) += {
      import java.util.jar.{Attributes, Manifest}
      val manifest = new Manifest
//            manifest.getMainAttributes().put(Attributes.Name.SEALED, "true")
      manifest.getMainAttributes.put(new Attributes.Name("Premain-Class"), "Agent")
      manifest.getMainAttributes.put(new Attributes.Name("Can-Redefine-Classes"), "true")
      //      manifest.getAttributes("Can-Redefine-Classes").put(Attributes.Name.SEALED, "true")
      Package.JarManifest(manifest)
    }
    ) settings (standardSettings: _*)


  //  Can-Redefine-Classes: true

  // Scalac command line options to install our compiler plugin.
  lazy val useAgentSettings = Seq(
    libraryDependencies ++= Seq(Javassist),
    fork in run := true,
    javaOptions in run ++= {
      val jar = (Keys.`package` in(agent, Compile)).value
      val addAgent = "-javaagent:" + jar.getAbsolutePath
      // add plugin timestamp to compiler options to trigger recompile of
      // main after editing the agent. (Otherwise a 'clean' is needed.)
      val dummy = "-Ddummy=" + jar.lastModified
      val asm = (managedClasspath in Compile).value.collectFirst {
        case file: Attributed[File] if file.data.absolutePath.contains("javassist") =>
          file.data.absolutePath
      }.getOrElse(sys.error("Could not find javassist.jar"))

      Seq(addAgent, dummy/*, "-jar " + asm*/)
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

