import sbt._

object DependencyManagement {

  /** Excluded and version managed transitive artifacts */
  def ivyExclusionsAndOverrides =
    <dependencies>
      <exclude org="org.junit" rev="*"/>
      <exclude org="org.junit" rev="4.8.1"/>
      <exclude org="colt" rev="1.2.0"/>
      <exclude org="blas" module="blas" rev="*"/>
      <exclude org="javax.script" rev="*"/>
      <exclude org="org.scala-tools.testing" module="scalacheck_2.8.0" rev="1.7"/>
      <override org="junit" module="junit" rev="4.8.1"/>
      <exclude org="org.eclipse" rev="*"/>
    </dependencies>

  /** Utilities for File IO */
  def CommonsIo = "commons-io" % "commons-io" % "2.4"

  /** General utilities for Java language */
  def CommonsLang = "commons-lang" % "commons-lang" % "2.6"

  /** General utilities for Java language */
  def CommonsExec = "org.apache.commons" % "commons-exec" % "1.2"

  /** Scala library providing Actors and Promises (for concurrency), and functional programming tools */
  def ScalazCore = "org.scalaz" %% "scalaz-core" % "7.1.0"

  def ScalazConcurrent = "org.scalaz" %% "scalaz-concurrent" % "7.1.0"

  def SpringCore = "org.springframework" % "spring-core" % "4.1.0.RELEASE"

  /**
   * Specs, unit testing framework
   *
   * http://code.google.com/p/specs/
   */
  def Specs = "org.specs2" %% "specs2" % "2.4.2" % "test" intransitive()

  def JUnit = "junit" % "junit" % "4.11" % "test" intransitive()

  /**
   * Scalacheck, automated unit testing using randomized test cases.
   *
   * http://code.google.com/p/scalacheck/
   *
   * We use this through Specs.
   */
  def Scalacheck = "org.scalacheck" %% "scalacheck" % "1.11.5" % "test"

  /** Dependency of Specs */
  def MockitoAll = "org.mockito" % "mockito-all" % "1.9.5" % "test"

  //  def TreeHugger = "com.eed3si9n" % "treehugger_2.9.1" % "0.2.1"
  def TreeHugger = "com.eed3si9n" % "treehugger_2.9.1" % "0.3.0"

  /** Date and Time represenation */
  def JodaTime = "joda-time" % "joda-time" % "2.4"

  def JodaConvert = "org.joda" % "joda-convert" % "1.7"

  def ScalaMeter = "com.github.axel22" %% "scalameter" % "0.3"

  /**
   * Collections specialized for primitive elements
   * http://fastutil.dsi.unimi.it/
   */
  def Fastutil = "it.unimi.dsi" % "fastutil" % "6.5.15"

  /**
   * kiama A Scala library for language processing
   *
   * http://code.google.com/p/kiama/
   */
  def Kiama = "com.googlecode.kiama" %% "kiama" % "1.5.2"

  /**
   * Scopt, command line parsing
   */
  def Scopt = "com.github.scopt" %% "scopt" % "3.2.0"

  /**
   * Database abstraction
   */
  def Slick = "com.typesafe.slick" %% "slick" % "2.0.1"

  /**
   * https://github.com/tototoshi/slick-joda-mapper
   *
   * Persists DateTime, Instant, LocalDateTime, LocalDate, LocalTime, DateTimeZone with Slick.
   */
  def SlickJodaMapper = "com.github.tototoshi" %% "slick-joda-mapper" % "1.1.0"

  def Slf4J = "org.slf4j" % "slf4j-nop" % "1.7.7"

  def H2 = "com.h2database" % "h2" % "1.4.177"

  def SqLite = "org.xerial" % "sqlite-jdbc" % "3.7.2"

  def JAnalyse = "fr.janalyse" %% "janalyse-series" % "1.6.1" % "compile"

  /**
   * Databinder Dispatch
   *
   * http://dispatch.databinder.net
   *
   * Scala API for HTTP client..
   */
  def Dispatch = "net.databinder.dispatch" %% "dispatch-core" % "0.11.0"

  private val ArgonautVersion: String = "6.0.4"

  /**
   * JSon library
   *
   * http://argonaut.io
   *
   * It's functional and nice but chokes on jsons from Yahoo... so it's unusable...
   */
  def Argonaut = "io.argonaut" %% "argonaut" % ArgonautVersion

  def ArgonautUnfiltered = "io.argonaut" %% "argonaut-unfiltered" % ArgonautVersion

  /**
   * JSon library from play
   */
  def PlayJson = "com.typesafe.play" %% "play-json" % "2.2.3"

  /**
   * Time series analysis
   *
   * http://saddle.github.io
   */
  def Saddle = "org.scala-saddle" %% "saddle-core" % "1.3.+"
}