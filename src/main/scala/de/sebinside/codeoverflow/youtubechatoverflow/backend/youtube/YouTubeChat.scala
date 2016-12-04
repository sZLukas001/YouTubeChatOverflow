package de.sebinside.codeoverflow.youtubechatoverflow.backend.youtube

import java.util.Calendar

import com.google.api.services.youtube.model.LiveChatMessage
import de.sebinside.codeoverflow.youtubechatoverflow.backend.YouTubeMessageProvider

import scala.collection.mutable

/**
  * Created by renx on 03.12.16.
  */
class YouTubeChat(broadCastID: String) extends YouTubeMessageProvider {

  private val liveChatID = YouTubeApiUtils.getLiveChatID(broadCastID) match {
    case Some(id) => id
    case None => throw new IllegalArgumentException("Invalid broadcast ID!")
  }

  private val messages = mutable.HashMap[Long, LiveChatMessage]()

  private val t = new java.util.Timer
  private val task = new java.util.TimerTask {
    override def run(): Unit = {
      for (item: LiveChatMessage <- YouTubeApiUtils.getLiveChatMessages(liveChatID)) {
        messages(item.getSnippet.getPublishedAt.getValue) = item
      }
    }
  }

  private[backend] def startPullingMessages(interval: Long = 1000L, delay: Long = 0L): Unit = t.schedule(task, delay, interval)

  private[backend] def stopPullingMessages: Boolean = task.cancel

  override private[backend] def getMessages = ???

  override private[backend] def getMessages(lastMilliseconds: Long) = {
    val currentTime = Calendar.getInstance.getTimeInMillis
    messages.filter(m => m._2.getSnippet.getPublishedAt.getValue > currentTime - lastMilliseconds).values.toList
  }
}

object YouTubeChat {

  def apply(broadCastID: String): YouTubeChat = new YouTubeChat(broadCastID)

}
