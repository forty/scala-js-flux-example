package example.utils

import scala.scalajs.js
import org.scalajs.dom
import example.models.{RawMessage, Message}
import example.actions.ChatServerActionCreators

object ChatWebAPIUtils {

  def getAllMessages() {
     // simulate retrieving data from a database
    val unparsedMessages = js.JSON.parse(dom.localStorage.getItem("message").toString()).asInstanceOf[js.Array[js.Dictionary[js.Any]]]

    val messages = unparsedMessages.map(RawMessage.fromJS(_: js.Dictionary[js.Any])).toList
    
    // simulate success callback
    ChatServerActionCreators.receiveAll(messages)
  }
  
  def createMessage(message: Message, threadName: String) {
     // simulate writing to a database
    val rawMessages = js.JSON.parse(dom.localStorage.getItem("message").toString()).asInstanceOf[js.Array[js.Any]]
    
    val rawMessage = message.toRawMessage(threadName)
    
    val updatedRawMessages = rawMessages ++ js.Array(rawMessage.toJS)
    
    dom.localStorage.setItem("message", js.JSON.stringify(updatedRawMessages))
    
    // simulate success callback
    dom.setTimeout({ () =>
      ChatServerActionCreators.receiveCreatedMessage(rawMessage)
    }, 0)
  }

}