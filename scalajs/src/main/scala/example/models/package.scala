package example

import java.util.Date
import scala.scalajs.js

package object models {
  
  case class RawMessage(id: String, threadID: String, authorName: String, threadName: String, timestamp: Long, text: String) {
    def toMessage(currentThreadID: String) = {
      Message(id, threadID, authorName, timestamp, text, threadID == currentThreadID)
    }
  }

  case class Message(id: String, threadID: String, authorName: String, timestamp: Long, text: String, isRead: Boolean) {
    def toRawMessage(threadName:String) = {
      RawMessage(id, threadID, authorName, threadName, timestamp, text)
    }
  }

  case class Thread(id: String, name: String, lastMessage: Message) {
    def lastTimestamp = lastMessage.timestamp
  }

}
