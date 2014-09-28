package example.components

import com.xored.scalajs.react._

object ChatApp extends TypedReactSpec {
  case class State()
  case class Props()
  
  def getInitialState(self: This) = State()

  @scalax
  def render(self: This) = {
    <div className="chatapp">
      {
        ThreadSection(ThreadSection.Props())
      }
      {
        MessageSection(MessageSection.Props())
      }
    </div>
  }

}