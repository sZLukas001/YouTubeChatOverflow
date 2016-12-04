package de.sebinside.codeoverflow.youtubechatoverflow.backend

import com.google.api.services.youtube.model.LiveChatMessage

/**
  * Created by sebastian on 04.12.2016.
  */
trait YouTubeMessageProvider {

  private[backend] def getMessages: List[LiveChatMessage]

  private[backend] def getMessages(lastMilliseconds: Long): List[LiveChatMessage]

}
