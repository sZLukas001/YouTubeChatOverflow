package de.sebinside.codeoverflow.youtubechatoverflow.backend.provider.youtube

import java.util.Calendar

import com.google.api.services.youtube.model.LiveChatMessage
import de.sebinside.codeoverflow.youtubechatoverflow.backend.provider.YouTubeMessageProvider

import scala.collection.JavaConversions._
import scala.collection.immutable

/**
  * Created by renx on 03.12.16.
  */
class YouTubeChat(broadCastID: String) extends YouTubeMessageProvider {

  val orderMsgByTimeAndId: Ordering[LiveChatMessage] = Ordering[(Long, String)].on[LiveChatMessage](msg => (msg.getSnippet.getPublishedAt.getValue, msg.getId))
  private val liveChatID = YouTubeApiUtils.getLiveChatID(broadCastID) match {
    case Some(id) => id
    case None => throw new IllegalArgumentException("Invalid broadcast ID!")
  }
  private val t = new java.util.Timer
  private val task = new java.util.TimerTask {
    override def run(): Unit = {
      for (item: LiveChatMessage <- YouTubeApiUtils.getLiveChatMessages(liveChatID)) {
        messages = messages + item
      }
    }
  }
  private var messages = immutable.SortedSet[LiveChatMessage]()(orderMsgByTimeAndId)

  // FIXME: Do this at antoher place... or maybe stop it finally. Optional
  startPullingMessages()

  private[backend] def startPullingMessages(interval: Long = 1000L, delay: Long = 0L): Unit = t.schedule(task, delay, interval)

  private[backend] def stopPullingMessages: Boolean = task.cancel

  override private[backend] def getMessages = ???

  override private[backend] def getMessages(lastMilliseconds: Long) = {
    val currentTime = Calendar.getInstance.getTimeInMillis
    messages.filter(m => m.getSnippet.getPublishedAt.getValue > currentTime - lastMilliseconds).toList
  }
}

object YouTubeChat {

  def apply(broadCastID: String): YouTubeChat = new YouTubeChat(broadCastID)

}
