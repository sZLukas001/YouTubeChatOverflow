package de.sebinside.codeoverflow.youtubechatoverflow.project.olacolorcontroll

import com.decodified.scalassh.SshClient
import de.sebinside.codeoverflow.youtubechatoverflow.backend.evaluation.ChatEvaluation

/**
  * Created by seb on 12.12.16.
  */
private[olacolorcontroll] class OlaRedVsBlue extends OlaColorControl {
  override private[project] def getName = ???

  override private[project] def getDescription = ???

  override private[olacolorcontrol] def evaluate(evaluation: ChatEvaluation, client: SshClient) = ???
}

object OlaRedVsBlue {
  def apply: OlaRedVsBlue = new OlaRedVsBlue()
}