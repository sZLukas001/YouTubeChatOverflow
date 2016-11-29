package de.sebinside.codeoverflow.youtubechatoverflow.project

import scala.collection.mutable

/**
  * Created by seb on 29.11.2016.
  */
object ProjectRegistry {

  private val projects = mutable.Map[String, ChatProject]()

  def clear(): Unit = projects.clear()

  def registerAll(projects: Seq[ChatProject]): Unit = projects.foreach(f => register(f))

  def register(project: ChatProject): Unit = projects += project.getName -> project

  def exists(projectName: String): Boolean = projects.contains(projectName)

  def start(projectName: String): Unit = projects(projectName).start()

  def listProjects: Seq[String] = (for ((name, _) <- projects) yield name).toSeq
}
