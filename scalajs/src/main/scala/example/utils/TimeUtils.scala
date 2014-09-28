package example.utils

import scala.scalajs.js.Date

object TimeUtils {

  def formatTime(timestamp: Long) = {
    val date = new Date(timestamp)
    "%02d:%02d:%02d".format(date.getHours(), date.getMinutes(), date.getSeconds())
  }

}