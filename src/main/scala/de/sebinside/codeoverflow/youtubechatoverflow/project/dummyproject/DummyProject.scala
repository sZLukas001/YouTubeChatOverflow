package de.sebinside.codeoverflow.youtubechatoverflow.project.dummyproject

import com.google.api.services.youtube.model.LiveChatMessage
import de.sebinside.codeoverflow.youtubechatoverflow.backend.evaluation.ChatEvaluation
import de.sebinside.codeoverflow.youtubechatoverflow.project.ChatProject

/**
  * Created by seb on 29.11.2016.
  */
private[dummyproject] class DummyProject extends ChatProject {
  override private[project] def getName: String = "DummyProject"

  override private[project] def getDescription: String = "Just a demo project"

  override private[project] def start(evaluation: ChatEvaluation) = {

    while (true) {

      val messages: List[LiveChatMessage] = evaluation.getMessages(10000)

      for (message: LiveChatMessage <- messages) {
        println("%s%s: %s".
          format(verifiedSymbol(message.getAuthorDetails.getIsVerified), message.getAuthorDetails.getDisplayName, message.getSnippet.getDisplayMessage))
      }

      Thread.sleep(1000)

    }

    def verifiedSymbol(verified: Boolean) : String = if (verified) "*" else new String()

  }
}

object DummyProject {

  def apply(): DummyProject = new DummyProject()

}