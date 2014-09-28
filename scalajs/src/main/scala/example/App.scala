package example

import scala.scalajs.js
import org.scalajs.dom
import com.xored.scalajs.react.React

import example.stores._
import example.components.ChatApp
import example.utils.ChatWebAPIUtils

object ScalaJSExample extends js.JSApp {
  def main() {
    // this ensures all store objects are initialized and registered to the dispatcher
    MessageStore.init()
    ThreadStore.init()
    UnreadThreadStore.init()
    
    ChatExampleData.init()
    
    ChatWebAPIUtils.getAllMessages()
    
    React.renderComponent(
      ChatApp(ChatApp.Props()),
      dom.document.getElementById("react"))
  }
}
