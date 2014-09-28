package example.components

import com.xored.scalajs.react._

import example.models._
import example.stores.ThreadStore
import example.stores.MessageStore

object MessageSection extends TypedReactSpec {

  case class State(messages: List[Message], thread: Option[Thread])
  case class Props()

  def getInitialState(self: This) = State(MessageStore.getAllForCurrentThread, ThreadStore.getCurrent)

  override def componentDidMount(self: This) {
    scrollToBottom(self)
    MessageStore.addChangeListener(() => onChange(self))
    ThreadStore.addChangeListener(() => onChange(self))
  }

  override def componentWillUnmount(self: This) {
    MessageStore.removeChangeListener(() => onChange(self))
    ThreadStore.removeChangeListener(() => onChange(self))
  }

  // TODO: not implemented yet in scala js react
  def componentDidUpdate(self: This) {
    scrollToBottom(self)
  }

  private def onChange(self: This) {
    self.setState(getInitialState(self))
  }

  private def scrollToBottom(self: This) {
    val ul = self.refs("messageList").getDOMNode
    ul.scrollTop = ul.scrollHeight
  }

  @scalax
  def render(self: This) = {
    import self._

    val messageListItems = state.messages.map((message) => MessageListItem(MessageListItem.Props(message)))

    <div className="message-section">
      <h3 className="message-thread-heading">{ state.thread.map(_.name).getOrElse(null) }</h3>
      <ul className="message-list" ref="messageList">
        { messageListItems }
      </ul>
      { MessageComposer(MessageComposer.Props()) }
    </div>
  }

}