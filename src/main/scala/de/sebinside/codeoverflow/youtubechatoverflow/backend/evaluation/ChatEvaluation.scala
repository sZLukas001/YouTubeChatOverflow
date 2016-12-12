package de.sebinside.codeoverflow.youtubechatoverflow.backend.evaluation

import com.google.api.services.youtube.model.LiveChatMessage
import de.sebinside.codeoverflow.youtubechatoverflow.backend.YouTubeMessageProvider

/**
  * Created by seb on 29.11.2016.
  */
class ChatEvaluation(messageProvider: YouTubeMessageProvider) {

  /**
    * @return all messages as provided by the YouTubeMessageProvider
    */
  def getMessages : List[LiveChatMessage] = {
    messageProvider.getMessages
  }

  /**
    * @return all messages sent during the last n milliseconds as provided by the YouTubeMessageProvider
    */
  def getMessages(lastMilliseconds : Long): List[LiveChatMessage] = {
    messageProvider.getMessages(lastMilliseconds)
  }

  def getWordHistogram(lastMilliseconds: Long, predicate: Seq[String] => Seq[String] = identity): List[(String, Int)] = {
    getMessages(lastMilliseconds) //all messages of last n milliseconds
      .map(msg => msg.getSnippet.getDisplayMessage) //extract text from messages
      .flatMap(message => {
        val cleanedMessage = message.split("\\s+").map(_.toLowerCase.replaceAll("\\W", ""))
        predicate(cleanedMessage)
      }) //split to single words
      .groupBy(identity)
      .mapValues(array => array.size) //count number of occurences of every word
      .toList
  }
}

object ChatEvaluation {

  def apply(messageProvider: YouTubeMessageProvider): ChatEvaluation = new ChatEvaluation(messageProvider)

}