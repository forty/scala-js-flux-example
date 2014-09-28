package example.components

import com.xored.scalajs.react._
import example.models.Message
import example.utils.TimeUtils

object MessageListItem extends TypedReactSpec {

  case class State()
  case class Props(message: Message)

  def getInitialState(self: This) = State()

  @scalax
  def render(self: This) = {
    val message = self.props.message

    <li className="message-list-item">
      <h5 className="message-author-name">{ message.authorName }</h5>
      <div className="message-time">
        { TimeUtils.formatTime(message.timestamp) }
      </div>
      <div className="message-text">{ message.text }</div>
    </li>
  }

}