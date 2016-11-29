package de.sebinside.codeoverflow.youtubechatoverflow

import de.sebinside.codeoverflow.youtubechatoverflow.util.ArgsParser.parse

/**
  * Created by seb on 29.11.2016.
  */
object YouTubeChatOverflow {

  def main(args: Array[String]): Unit = {

    println("Hello YouTube!")

    // Parse args and start doing cool stuff!
    parse(args) { config =>

      if (config.listProjects) {

        // TODO: List all registered projects and exit
        println("TODO")

      } else {

        // Start doing cool stuff!
        println("You started with the ID \"%s\" and project name \"%s\"".
          format(config.broadcastId, config.projectName))

      }

    }

  }

}
