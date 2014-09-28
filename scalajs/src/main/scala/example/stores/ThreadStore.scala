package example.stores

import example.actions._
import example.models._
import example.utils.ChangeEventEmitter
import example.dispatcher.ChatAppDispatcher

object ThreadStore extends ChangeEventEmitter {
  
  def init() {
    // dummy method, needs to be called to trigger object initialization
  }

  private var _currentID: String = null
  private var _threads = Map[String, Thread]()

  def init(rawMessages: List[RawMessage]) {
    rawMessages.foreach({ message =>
      val threadID = message.threadID

      if (_threads.get(threadID).map(_.lastTimestamp > message.timestamp).getOrElse(false)) {
        return
      }
      _threads += (threadID -> Thread(threadID, message.threadName, message.toMessage(_currentID)))
    })
    if (_currentID == null) {
      val allChrono = getAllChrono()
      _currentID = allChrono.last.id
    }

    markLastMessageRead()
  }

  private def markLastMessageRead() {
    if (_currentID != null) {
      _threads += (_currentID -> _threads(_currentID).copy(lastMessage = _threads(_currentID).lastMessage.copy(isRead = true)))
    }
  }

  def get(id: String) = _threads.get(id)

  def getAll() = _threads

  def getAllChrono() = _threads.values.toList.sortBy(_.lastTimestamp)

  def getCurrentID() = _currentID

  def getCurrent() = _threads.get(_currentID)

  val dispatchToken = ChatAppDispatcher.register({
    case ClickThread(threadID) =>
      _currentID = threadID
      markLastMessageRead()
      emitChange()
      
    case ReceiveMessage(rawMessages) =>
      init(rawMessages)
      emitChange()
      
    case _ =>
  })

}