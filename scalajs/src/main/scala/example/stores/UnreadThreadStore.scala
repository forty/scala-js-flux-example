package example.stores

import example.actions._
import example.utils.ChangeEventEmitter
import example.dispatcher.ChatAppDispatcher

object UnreadThreadStore extends ChangeEventEmitter {
  
  def init() {
    // dummy method, needs to be called to trigger object initialization
  }

  def getCount() = {
    val threads = ThreadStore.getAll()
    threads.values.toList.count(!_.lastMessage.isRead)
  }

  val dispatchToken = ChatAppDispatcher.register({ (action) =>
  	ChatAppDispatcher.waitFor(List(ThreadStore.dispatchToken, MessageStore.dispatchToken))
    
    action match {
      case ClickThread(_) =>
        emitChange()
        
      case ReceiveMessage(_) =>
        emitChange()
        
      case _ =>
    }
  })

}