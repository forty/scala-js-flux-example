package example.components

import com.xored.scalajs.react._
import com.xored.scalajs.react.util._
import example.actions.ChatThreadActionCreators
import example.models._
import example.utils.TimeUtils

object ThreadListItem extends TypedReactSpec with TypedEventListeners {
  case class State()
  case class Props(thread: Thread, currentThreadID: String)

  def getInitialState(self: This) = State()

  @scalax
  def render(self: This) = {
    import self._
    
    val thread = props.thread
    val lastMessage = thread.lastMessage
    val currentThreadID = props.currentThreadID
    
    val onClick = button.onClick(e => {
      ChatThreadActionCreators.clickThread(props.thread.id)
    })
    
    <li 
    	className={ClassName(
	    	"thread-list-item" -> true,
	    	"active" -> (thread.id == currentThreadID)
	    )}
    	onClick={onClick}>

    	<h5 className="thread-name">{thread.name}</h5>
    	<div className="thread-time">
    		{TimeUtils.formatTime(lastMessage.timestamp)}
    	</div>
    	<div className="thread-last-message">
    		{lastMessage.text}
    	</div>
    </li>
  }
}