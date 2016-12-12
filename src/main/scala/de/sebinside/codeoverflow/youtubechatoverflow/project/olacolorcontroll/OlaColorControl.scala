package de.sebinside.codeoverflow.youtubechatoverflow.project.olacolorcontroll

import java.io.File

import com.decodified.scalassh.{HostConfig, PasswordLogin, SSH, SshClient}
import de.sebinside.codeoverflow.youtubechatoverflow.backend.evaluation.ChatEvaluation
import de.sebinside.codeoverflow.youtubechatoverflow.project.ChatProject

import scala.xml.{NodeSeq, XML}

/**
  * Created by seb on 12.12.16.
  */
abstract private[olacolorcontroll] class OlaColorControl extends ChatProject {

  // TODO: Read from args if possible
  val XMLFilePath = "src/main/resources/ssh_login.xml"

  protected def setColor(client: SshClient, universe: Int, data: (Int, Int, Int, Int)): Unit = {

    val command = "ola_streaming_client -u %d -d %d,%d,%d,%d"
    val value = command.format(universe, data._1, data._2, data._3, data._4)

    client.exec(value)
    println("Executed: \"%s\" via SSH.".format(value))

  }

  protected def evaluate(evaluation: ChatEvaluation, client: SshClient): Unit

  override private[project] def start(evaluation: ChatEvaluation) = {

    // Get Login Data
    val sshXML = readSshXML(XMLFilePath)

    // Create the connection and start the chat evaluation
    SSH(sshXML._1, sshXML._2) {
      client => evaluate(evaluation, client)
    }

  }

  private def readSshXML(filePath: String): (String, HostConfig) = {

    val sshXML: NodeSeq = XML.loadFile(new File(filePath))

    if (sshXML.isEmpty)
      throw new Exception("Invalid XML Format. Use {address, name, password} inside of {login}.")

    val sshAddress = (sshXML \ "address").text
    val sshName = (sshXML \ "name").text
    val sshPassword = (sshXML \ "password").text

    (sshAddress, HostConfig(PasswordLogin(sshName, sshPassword)))

  }


}
