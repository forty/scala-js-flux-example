package example.utils

import org.scalajs.dom
import example.models.{RawMessage, Message}
import example.actions.ChatServerActionCreators

object ChatWebAPIUtils {

  def getAllMessages() {
     // simulate retrieving data from a database
    val messages = upickle.read[List[RawMessage]](dom.localStorage.getItem("message").toString())
    // simulate success callback
    ChatServerActionCreators.receiveAll(messages)
  }
  
  def createMessage(message: Message, threadName: String) {
     // simulate writing to a database
    val rawMessages = upickle.read[List[RawMessage]](dom.localStorage.getItem("message").toString())
    
    val rawMessage = message.toRawMessage(threadName)
    
    val updatedRawMessages = rawMessages ++ List(rawMessage)
    
    dom.localStorage.setItem("message", upickle.write(updatedRawMessages))
    
    // simulate success callback
    dom.setTimeout({ () =>
      ChatServerActionCreators.receiveCreatedMessage(rawMessage)
    }, 0)
  }

}
