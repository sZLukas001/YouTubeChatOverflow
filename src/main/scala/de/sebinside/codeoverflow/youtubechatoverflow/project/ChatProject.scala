package de.sebinside.codeoverflow.youtubechatoverflow.project

/**
  * Created by seb on 29.11.2016.
  */
private[project] trait ChatProject {

  private[project] def getName: String

  private[project] def getDescription: String

  private[project] def start(): Unit
}
