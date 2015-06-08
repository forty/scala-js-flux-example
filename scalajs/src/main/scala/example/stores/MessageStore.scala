package example.stores

import example.actions._
import example.models._
import example.utils.ChangeEventEmitter
import example.dispatcher.ChatAppDispatcher
import java.lang.System

object MessageStore extends ChangeEventEmitter {
  
  def init() {
    // dummy method, needs to be called to trigger object initialization
  }

  private var _messages = Map[String, Message]()

  private def addMessages(rawMessages: List[RawMessage]) {
    rawMessages.foreach({ (message) =>
      if (!_messages.contains(message.id)) {
        _messages += (message.id -> message.toMessage(ThreadStore.getCurrentID))
      }
    })
  }

  private def markAllRead(threadID: String) {
    _messages.values.foreach({ (message) =>
      if (message.threadID == threadID && !message.isRead) {
        _messages += (message.id -> message.copy(isRead = true))
      }
    })
  }

  def get(id: String) = _messages.get(id)

  def getAll() = _messages

  def getAllForThread(threadID: String) = _messages.values.toList.filter(_.threadID == threadID).sortBy(_.timestamp)

  def getAllForCurrentThread() = getAllForThread(ThreadStore.getCurrentID)

  def getCreatedMessageData(text: String) = {
    val timestamp = System.currentTimeMillis()
    Message("m_" + timestamp, ThreadStore.getCurrentID, "Bill", timestamp, text, true) // name hardcoded for example
  }

  val dispatchToken = ChatAppDispatcher.register({
    case ClickThread(threadID) =>
      ChatAppDispatcher.waitFor(List(ThreadStore.dispatchToken))
      markAllRead(threadID)
      emitChange()

    case CreateMessage(text) =>
      val message = getCreatedMessageData(text)
      _messages += (message.id -> message)
      emitChange()

    case ReceiveMessage(rawMessages) =>
      addMessages(rawMessages)
      ChatAppDispatcher.waitFor(List(ThreadStore.dispatchToken))
      markAllRead(ThreadStore.getCurrentID)
      emitChange()

    case _ =>
  }:(Any=>Any))

}
