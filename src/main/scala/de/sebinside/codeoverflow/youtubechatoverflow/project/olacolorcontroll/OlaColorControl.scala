package de.sebinside.codeoverflow.youtubechatoverflow.project.olacolorcontroll

import com.decodified.scalassh.{HostConfig, PasswordLogin, SSH, SshClient}
import de.sebinside.codeoverflow.youtubechatoverflow.backend.evaluation.ChatEvaluation
import de.sebinside.codeoverflow.youtubechatoverflow.project.ChatProject

/**
  * Created by seb on 12.12.16.
  */
abstract private[olacolorcontroll] class OlaColorControl extends ChatProject {

  // TODO: Implement abstract behavior to simulate color changes

  override private[project] def start(evaluation: ChatEvaluation) = {

    // FIXME: Get adress, username and password from xml file provided at the args
    val config: HostConfig = HostConfig(PasswordLogin("userName", "password"))


    // TODO: Just sample Code from the website
    SSH("192.168.100.***", config) {
      { client =>
        client.exec("ls -a").right.map { result =>
          println("Result:\n" + result.stdOutAsString())
        }
      }

    }


  }

  private[olacolorcontrol] def evaluate(evaluation: ChatEvaluation, client: SshClient)


}
