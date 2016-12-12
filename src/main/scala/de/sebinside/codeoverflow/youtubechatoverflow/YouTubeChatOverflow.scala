package de.sebinside.codeoverflow.youtubechatoverflow

import de.sebinside.codeoverflow.youtubechatoverflow.backend.evaluation.ChatEvaluation
import de.sebinside.codeoverflow.youtubechatoverflow.backend.mockup.MockUpChat
import de.sebinside.codeoverflow.youtubechatoverflow.backend.youtube.YouTubeChat
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

        val youTubeChat =
          if (config.broadcastId.contains(".txt")) ChatEvaluation(MockUpChat("src/main/resources/" + config.broadcastId))
          else ChatEvaluation(YouTubeChat(config.broadcastId))

        ProjectRegistry.start(config.projectName, youTubeChat)

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
