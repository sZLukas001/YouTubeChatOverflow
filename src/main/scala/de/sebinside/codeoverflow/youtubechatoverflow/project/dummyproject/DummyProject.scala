package de.sebinside.codeoverflow.youtubechatoverflow.project.dummyproject

import de.sebinside.codeoverflow.youtubechatoverflow.project.ChatProject

/**
  * Created by seb on 29.11.2016.
  */
private[dummyproject] class DummyProject extends ChatProject {
  override private[project] def getName: String = "DummyProject"

  override private[project] def getDescription: String = "Just a demo project"

  override private[project] def start(): Unit = println("I'm a dummy!")
}

object DummyProject {

  def apply(): DummyProject = new DummyProject()

}