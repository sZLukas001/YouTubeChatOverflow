package de.sebinside.codeoverflow.youtubechatoverflow.backend

import java.util.Calendar

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.{LiveChatMessage, LiveChatMessageAuthorDetails, LiveChatMessageSnippet}

import scala.io.Source

/**
  * Created by renx on 05.12.16.
  */
class MockUpChat(fileName: String) extends YouTubeMessageProvider {

  private var id = 0
  // Example: "*Andre;This is an example chat message;1000"
  private val messageStringRegex = "^(\\*)?([^;]+?)\\s*;\\s*([^;]+?)(?:;(\\d+))?$".r
  private val time = Calendar.getInstance.getTimeInMillis

  def createLiveChatMessage(messageString: String) : Option[LiveChatMessage] = {
    messageString match
    {
      case messageStringRegex(verified, authorName, message, timeDelta) =>
        Option(new LiveChatMessage()
          .setId((id += 1).toString)
          .setAuthorDetails(new LiveChatMessageAuthorDetails()
            .setDisplayName(authorName)
            .setIsVerified(verified != null))
          .setSnippet(new LiveChatMessageSnippet()
            .setDisplayMessage(message)
            .setPublishedAt(new DateTime(time + (if(timeDelta == null) 0 else timeDelta.toLong)))))
      case _ => Option.empty
    }
  }

  val messages : List[LiveChatMessage] = Source.fromFile(fileName).getLines.map(string => createLiveChatMessage(string)).flatten.toList

  override private[backend] def getMessages: List[LiveChatMessage] = messages

  override private[backend] def getMessages(lastMilliseconds: Long): List[LiveChatMessage] = {
    val currentTime = Calendar.getInstance.getTimeInMillis
    messages.filter(m => currentTime > m.getSnippet.getPublishedAt.getValue &&
      m.getSnippet.getPublishedAt.getValue > currentTime - lastMilliseconds)
  }
}

object MockUpChat {

  def apply(fileName: String): MockUpChat = new MockUpChat(fileName)

}
