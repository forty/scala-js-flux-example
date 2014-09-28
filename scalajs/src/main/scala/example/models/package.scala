package example

import java.util.Date
import scala.scalajs.js

package object models {
  
  object RawMessage {
    def fromJS(dict : js.Dictionary[js.Any]) = {
      RawMessage(
          dict("id").asInstanceOf[js.String],
          dict("threadID").asInstanceOf[js.String],
          dict("authorName").asInstanceOf[js.String],
          dict("threadName").asInstanceOf[js.String],
          dict("timestamp").asInstanceOf[js.Number].longValue,
          dict("text").asInstanceOf[js.String]
      )
    }
  }

  case class RawMessage(id: String, threadID: String, authorName: String, threadName: String, timestamp: Long, text: String) {
    def toMessage(currentThreadID: String) = {
      Message(id, threadID, authorName, timestamp, text, threadID == currentThreadID)
    }
    
    def toJS = {
      js.Dynamic.literal("id" -> id, "threadID" -> threadID, "authorName" -> authorName, "threadName" -> threadName, "timestamp" -> timestamp, "text" -> text)
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