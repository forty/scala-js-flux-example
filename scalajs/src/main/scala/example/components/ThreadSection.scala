package example.components

import com.xored.scalajs.react._

import example.models._
import example.stores.ThreadStore
import example.stores.UnreadThreadStore


object ThreadSection extends TypedReactSpec {
  case class State(threads: List[Thread], currentThreadID: String, unreadCount: Int)
  case class Props()

  def getInitialState(self: This) = State(ThreadStore.getAllChrono(), ThreadStore.getCurrentID, UnreadThreadStore.getCount())

  override def componentDidMount(self: This) {
    ThreadStore.addChangeListener(() => onChange(self))
    UnreadThreadStore.addChangeListener(() => onChange(self))
  }

  override def componentWillUnmount(self: This) {
    ThreadStore.removeChangeListener(() => onChange(self))
    UnreadThreadStore.removeChangeListener(() => onChange(self))
  }

  private def onChange(self: This) {
    self.setState(getInitialState(self))
  }

  @scalax
  def render(self: This) = {
    import self._

    val threadListItems = state.threads.map { (thread) =>
      ThreadListItem(ThreadListItem.Props(thread, state.currentThreadID))
    }

    <div className="thread-section">
      <div className="thread-count">
        { if(state.unreadCount != 0) (<span> Unread threads: {state.unreadCount} </span>) }
      </div>
      <ul className="thread-list">
        { threadListItems }
      </ul>
    </div>
  }
}