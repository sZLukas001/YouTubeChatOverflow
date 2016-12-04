package de.sebinside.codeoverflow.youtubechatoverflow.backend.evaluation

import com.google.api.services.youtube.model.LiveChatMessage
import de.sebinside.codeoverflow.youtubechatoverflow.backend.YouTubeMessageProvider

/**
  * Created by seb on 29.11.2016.
  */
public class ChatEvaluation(messageProvider: YouTubeMessageProvider) {

  /**
    * @return all messages as provided by the YouTubeMessageProvider
    */
  def getMessages : List[LiveChatMessage] = {
    messageProvider.getMessages
  }

  /**
    * @return all messages sent during the last n milliseconds as provided by the YouTubeMessageProvider
    */
  def getMessages(lastMilliseconds : Long) = {
    messageProvider.getMessages(lastMilliseconds)
  }

}

object ChatEvaluation {

  def apply(messageProvider: YouTubeMessageProvider): ChatEvaluation = new ChatEvaluation(messageProvider)

}