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

  def getMostUsedWord(lastMilliseconds : Long): String = {
    getMessages(lastMilliseconds) //all messages of last n milliseconds
      .map(msg => msg.getSnippet.getDisplayMessage) //extract text from messages
      .mkString(" ") //concat messages using " "
      .split("\\s") //split to single words
      .groupBy(msg => msg.toLowerCase.replaceAll("\\W", ""))
      //group by words, map words to lowercase and replace non character signs to find duplicates better
      .mapValues(array => array.size) //count number of occurences of every word
      .maxBy(_._2) //find tuple (most used word, number of uses)
      ._1 //get word from tuple
  }
}

object ChatEvaluation {

  def apply(messageProvider: YouTubeMessageProvider): ChatEvaluation = new ChatEvaluation(messageProvider)

}