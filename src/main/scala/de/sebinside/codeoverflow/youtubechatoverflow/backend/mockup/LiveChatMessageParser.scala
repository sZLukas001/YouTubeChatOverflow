package de.sebinside.codeoverflow.youtubechatoverflow.backend.mockup

import java.util.Calendar

import com.google.api.client.util.DateTime
import com.google.api.services.youtube.model.{LiveChatMessage, LiveChatMessageAuthorDetails, LiveChatMessageSnippet}

import scala.util.Properties
import scala.util.parsing.combinator.JavaTokenParsers

/**
  * Created by renx on 12.12.16.
  */
object LiveChatMessageParser extends JavaTokenParsers {

  override val skipWhitespace = false

  private val eol = Properties.lineSeparator
  private val eoi = """\z""".r
  private val separator = eoi | eol
  private val string : Parser[String] = "[^;]+".r
  private val sponsor : Parser[String] = "*"
  private val author : Parser[LiveChatMessageAuthorDetails] = sponsor.? ~ string ^^ {
    case Some(sponsor) ~ name => new LiveChatMessageAuthorDetails().setIsChatSponsor(true).setDisplayName(name)
    case None ~ name => new LiveChatMessageAuthorDetails().setIsChatSponsor(false).setDisplayName(name)
  }
  private val number : Parser[Long] =  "(-?\\d+)".r ^^ { _.toLong }
  private val time : Parser[Long] = ";" ~> number
  private val message : Parser[LiveChatMessageSnippet] = string ~ time.? ^^ {
    case messageString ~ Some(time) => new LiveChatMessageSnippet().setDisplayMessage(messageString)
      .setPublishedAt(new DateTime(Calendar.getInstance.getTimeInMillis + time))
    case messageString ~ None => new LiveChatMessageSnippet().setDisplayMessage(messageString)
      .setPublishedAt(new DateTime(Calendar.getInstance.getTimeInMillis))
  }
  private val liveChatMessage : Parser[LiveChatMessage] = author ~ (";" ~> message) ^^ {
    case author ~ message => new LiveChatMessage().setAuthorDetails(author).setSnippet(message)
  }
  private val liveChatMessages : Parser[List[LiveChatMessage]] = rep(liveChatMessage <~ separator)

  def apply(in: String) : List[LiveChatMessage] = parseAll(liveChatMessages, in) match {
    case Success(result, _) => result
    case Error(msg, _) =>
      println("LiveChatMessageParser-ERROR: %s". format(msg))
      List[LiveChatMessage]()
    case Failure(msg, _) =>
      println("LiveChatMessageParser-FAILURE: %s". format(msg))
      List[LiveChatMessage]()
  }
}
