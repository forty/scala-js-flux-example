package example.components

import com.xored.scalajs.react._
import com.xored.scalajs.react.util.TypedEventListeners
import example.models._
import example.actions.ChatMessageActionCreators

object MessageComposer extends TypedReactSpec with TypedEventListeners {

  val ENTER_KEY_CODE = 13

  case class State(text: String)
  case class Props()

  def getInitialState(self: This) = State("")

  @scalax
  def render(self: This) = {
    import self._
    
    val onChange = input.onChange(e => {
      setState(State(e.target.value))
    })

    val onKeyDown = input.onKeyPress(e => {
      if (e.keyCode == ENTER_KEY_CODE) {
        val text = state.text.trim()
        if (!text.isEmpty()) {
          ChatMessageActionCreators.createMessage(text)
        }
        setState(State(""))
      }
    })
    
    <textarea className="message-composer" name="message" value={ state.text } onChange={ onChange } onKeyDown={ onKeyDown }/>
  }

}