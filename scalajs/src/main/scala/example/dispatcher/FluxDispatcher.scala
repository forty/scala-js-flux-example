package example.dispatcher

import scala.scalajs.js
import scala.scalajs.js.annotation.JSName

@JSName("Flux.Dispatcher")
class FluxDispatcher extends js.Object {

  def dispatch(message: Any): Unit = ???
  def waitFor(token: String): Unit = ???
  def waitFor(token: List[String]): Unit = ???
  def register(handler: js.Function): String = ???

}
