package example

import scala.scalajs.js
import org.scalajs.dom
import example.models._

object ChatExampleData {

  def init() {
    val messages = Array(RawMessage("m_1", "t_1", "Bill", "Jing and Bill", System.currentTimeMillis() - 99999, "Hey Jing, want to give a Flux talk at ForwardJS?"),
      RawMessage("m_2", "t_1", "Bill", "Jing and Bill", System.currentTimeMillis() - 89999, "Seems like a pretty cool conference."),
      RawMessage("m_3", "t_1", "Jing", "Jing and Bill", System.currentTimeMillis() - 79999, "Sounds good. Will they be serving dessert?"),
      RawMessage("m_4", "t_2", "Bill", "Dave and Bill", System.currentTimeMillis() - 69999, "Hey Dave, want to get a beer after the conference?"),
      RawMessage("m_5", "t_2", "Dave", "Dave and Bill", System.currentTimeMillis() - 59999, "Totally! Meet you at the hotel bar."),
      RawMessage("m_6", "t_3", "Bill", "Functional Heads", System.currentTimeMillis() - 49999, "Hey Brian, are you going to be talking about functional stuff?"),
      RawMessage("m_7", "t_3", "Brian", "Brian and Bill", System.currentTimeMillis() - 39999, "At ForwardJS? Yeah, of course. See you there!"))

    val messagesJS = upickle.write(messages)
    
    dom.localStorage.clear()
    dom.localStorage.setItem("message", messagesJS)
  }

}
