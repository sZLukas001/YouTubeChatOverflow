package de.sebinside.codeoverflow.youtubechatoverflow.backend.provider.youtube

import java.util

import com.google.api.client.auth.oauth2.Credential
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.LiveChatMessage

/**
  * Created by seb on 12.12.16.
  */
private[youtube] object YouTubeApiUtils {

  private val youtube: YouTube = {
    val scopes: java.util.List[String] = new util.ArrayList[String]
    scopes.add("https://www.googleapis.com/auth/youtube.readonly")
    val credential: Credential = Auth.authorize(scopes, "listbroadcasts")

    new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
      .setApplicationName("YouTubeChatOverflow").build
  }

  private[youtube] def getLiveChatID(broadcastID: String): Option[String] = {
    val broadcastsResponse = youtube.liveBroadcasts.list("snippet").setId(broadcastID).execute
    if (broadcastsResponse.getItems.size > 0) {
      val broadcast = broadcastsResponse.getItems.get(0)

      Option(broadcast.getSnippet.getLiveChatId)
    } else {
      Option.empty
    }
  }

  private[youtube] def getLiveChatMessages(liveChatID: String): java.util.List[LiveChatMessage] = {
    val liveChatResponse = youtube.liveChatMessages.list(liveChatID, "snippet, authorDetails").execute

    liveChatResponse.getItems
  }
}
