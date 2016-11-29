package de.sebinside.codeoverflow.youtubechatoverflow.util

/**
  * Created by seb on 29.11.2016.
  */
object ArgsParser {

  private val PROGRAM_NAME = "YouTube Chat Overflow"
  private val PROGRAM_DESC = "Provides several methods for giving the youtube chat control."

  private val argsParser = new scopt.OptionParser[Config](PROGRAM_NAME) {
    head(PROGRAM_DESC)

    arg[String]("<broadcast id>").optional().action((value, config) =>
      config.copy(broadcastId = value)).
      // TODO: Add a validation method (also check for list flag)
      validate(value => if (true) success else failure("Just for demonstration, add ID validation later")).
      text("Specify the broadcast id of the (live) broadcast to work with.")

    opt[String]('p', "project").valueName("<Project Name>").action((value, config) =>
      config.copy(projectName = value)).
      // TODO: Add a validation method
      validate(value => if (true) success else failure("Just for demonstration. add project name validation")).
      text("Specify a chat project to start. Get a list of available projects with --list.")

    opt[Unit]('l', "list").action((_, config) =>
      config.copy(listProjects = true)).
      text("Get a list of all available chat projects. Exit after print.")

    help("help").hidden().text("prints help")

  }

  /**
    * Parses command line input and executes the code with the given configuration.
    *
    * @param args The command line arguments from the main-method
    * @param code The code to run
    */
  def parse(args: Array[String])(code: Config => Unit): Unit = {

    argsParser.parse(args, Config()) match {
      case None => // argument fail
      case Some(config) => code(config)
    }

  }

  private case class Config(broadcastId: String = "",
                            projectName: String = "",
                            listProjects: Boolean = false
                           )


}
