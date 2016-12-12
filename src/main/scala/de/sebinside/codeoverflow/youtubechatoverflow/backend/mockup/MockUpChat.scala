package de.sebinside.codeoverflow.youtubechatoverflow.backend.mockup

import java.util.Calendar

import com.google.api.services.youtube.model.LiveChatMessage
import de.sebinside.codeoverflow.youtubechatoverflow.backend.YouTubeMessageProvider

import scala.io.Source

/**
  * Created by renx on 05.12.16.
  */
class MockUpChat(fileName: String) extends YouTubeMessageProvider {
  val messages : List[LiveChatMessage] = LiveChatMessageParser(Source.fromFile(fileName).mkString)

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
