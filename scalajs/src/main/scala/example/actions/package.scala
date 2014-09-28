package example

import example.models._
import example.stores._
import example.dispatcher.ChatAppDispatcher
import example.utils.ChatWebAPIUtils

package object actions {

  sealed trait ChatAppAction

  trait ServerAction extends ChatAppAction
  trait ViewAction extends ChatAppAction

  case class CreateMessage(text: String) extends ViewAction
  case class ClickThread(threadID: String) extends ViewAction
  case class ReceiveMessage(rawMessages: List[RawMessage]) extends ServerAction
  case class ReceiveCreatedMessage(rawMessage: RawMessage) extends ServerAction

  object ChatThreadActionCreators {
    def clickThread(threadID: String) {
      ChatAppDispatcher.dispatch(ClickThread(threadID))
    }
  }

  object ChatMessageActionCreators {
    def createMessage(text: String) {
      ChatAppDispatcher.dispatch(CreateMessage(text))
      val message = MessageStore.getCreatedMessageData(text)
      val threadName = ThreadStore.getCurrent.map(_.name).getOrElse("")
      ChatWebAPIUtils.createMessage(message, threadName)
    }
  }

  object ChatServerActionCreators {
    def receiveAll(rawMessages: List[RawMessage]) {
      ChatAppDispatcher.dispatch(ReceiveMessage(rawMessages))
    }
    def receiveCreatedMessage(createdMessage: RawMessage) {
      ChatAppDispatcher.dispatch(ReceiveCreatedMessage(createdMessage))
    }
  }

}