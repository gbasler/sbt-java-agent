import sbt._
import sbt.Keys._
import DependencyFilter._

object JavaAgentPlugin extends Plugin {

  lazy val javaAgentSettings = Seq(
    javaOptions in run <++= createJvmOptions,
    fork in run := true
  )

  def findAgents = (update) map { report: UpdateReport =>
    report.select(fnToConfigurationFilter("agent" == _))
  }

  def createJvmOptions = (findAgents) map { (agentsFiles: Seq[File]) =>
    agentsFiles.map("-javaagent:" + _.getAbsolutePath)
  }
}