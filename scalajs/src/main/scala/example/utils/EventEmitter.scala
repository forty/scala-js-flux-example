package example.utils

class EventEmitter {

  type Callback = () => Unit

  private case class Listener(event: String, cb: Callback, origCb: Option[Callback])
  private var _listeners = List[Listener]()

  def emit(event: String) {
    _listeners.filter(_.event == event).foreach(_.cb())
  }

  def on(event: String, callback: Callback) {
    _listeners ::= Listener(event, callback, None)
  }

  def addListener(event: String, callback: Callback) {
    on(event, callback)
  }

  def once(event: String, callback: Callback) {
    var fired = false
    val autoRemoveCb = { () =>
      removeListener(event, callback)
      if (!fired) {
        fired = true
        callback()
      }
    }
    _listeners ::= Listener(event, autoRemoveCb, Some(callback))
  }

  def removeListener(event: String, callback: Callback) {
    _listeners = _listeners.filterNot({
      case Listener(e, cb, Some(origCb)) => e == event && (cb == callback || origCb == callback)
      case Listener(e, cb, _) => e == event && cb == callback
      case _ => false
    })
  }

  def removeAllListeners(event: String) {
    _listeners = _listeners.filter({
      case Listener(e, _,_) => e != event
      case _ => true
    })
  }

  def removeAllListeners() {
    _listeners = List()
  }

}