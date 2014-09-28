package example.dispatcher

class Dispatcher[Payload <: AnyRef] {

  type Callback = (Payload) => Unit

  private var lastId = 1
  private val prefix = "ID_"

  private var _callbacks = Map[String, Callback]()
  private var _isPending = Map[String, Boolean]()
  private var _isHandled = Map[String, Boolean]()
  private var _isDispatching = false
  private var _pendingPayload: Option[Payload] = None
  
  def isDispatching = _isDispatching
  def isPending(id:String) = _isPending.getOrElse(id, false)
  def isHandled(id:String) = _isHandled.getOrElse(id, false)
  
  def assert(condition: => Boolean, text: String, args: String*) {
    if (!condition) {
      throw new RuntimeException(text.format(args: _*))
    }
  }

  def register(callback: Callback) = {
    lastId += 1
    val id = prefix + lastId
    _callbacks += (id -> callback)
    id
  }

  def unregister(id: String) {
    assert(_callbacks.contains(id), "Dispatcher.unregister(...): `%s` does not map to a registered callback.", id)
    _callbacks -= id
  }

  def waitFor(ids: List[String]) {
    assert(_isDispatching, "Dispatcher.waitFor(...): Must be invoked while dispatching.")
    ids.foreach { id =>
      if (isPending(id)) {
        assert(isHandled(id), "Dispatcher.waitFor(...): Circular dependency detected while waiting for `%s`.", id)
      } else {
        assert(_callbacks.contains(id), "Dispatcher.waitFor(...): `%s` does not map to a registered callback.", id)
        invokeCallback(id)
      }
    }
  }

  def dispatch(payload: Payload) {
    assert(!_isDispatching, "Dispatch.dispatch(...): Cannot dispatch in the middle of a dispatch.")
    startDispatching(payload)
    try {
      _callbacks.foreach {
        case (id, _) =>
          if (!isPending(id)) {
            invokeCallback(id)
          }
      }
    } finally {
      stopDispatching()
    }
  }

  private def invokeCallback(id: String) {
	  _isPending += (id -> true)
	  _callbacks(id)(_pendingPayload.get)
	  _isHandled += (id -> true)
  }

  private def startDispatching(payload: Payload) {
	  _isPending = Map[String, Boolean]()
	  _isHandled = Map[String, Boolean]()
	  _pendingPayload = Some(payload)
	  _isDispatching = true
  }

  private def stopDispatching() {
	  _pendingPayload = None
	  _isDispatching = false
  }

}