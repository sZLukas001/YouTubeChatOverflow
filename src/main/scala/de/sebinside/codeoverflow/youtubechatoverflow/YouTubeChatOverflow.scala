package de.sebinside.codeoverflow.youtubechatoverflow

import de.sebinside.codeoverflow.youtubechatoverflow.project.ProjectRegistry
import de.sebinside.codeoverflow.youtubechatoverflow.project.dummyproject.DummyProject
import de.sebinside.codeoverflow.youtubechatoverflow.util.ArgsParser.parse

/**
  * Created by seb on 29.11.2016.
  */
object YouTubeChatOverflow {

  def main(args: Array[String]): Unit = {
    initProjects()

    println("Hello YouTube!")

    // Parse args and start doing cool stuff!
    parse(args) { config =>

      if (config.listProjects) {

        println("Available Projects:\n%s".format(ProjectRegistry.listProjects.mkString(", ")))

      } else if (ProjectRegistry.exists(config.projectName)) {

        // Start doing cool stuff!
        println("You started with the ID \"%s\" and project name \"%s\"".
          format(config.broadcastId, config.projectName))

        ProjectRegistry.start(config.projectName)

      } else {

        // Did not find project
        println("Unable to find project with name \"%s\".".format(config.projectName))

      }

    }

  }

  def initProjects(): Unit = ProjectRegistry.registerAll(Seq(
    DummyProject()
  ))


}
