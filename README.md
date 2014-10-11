scala-js-flux-example
=====================

This is a reimplementation of [Facebook Flux chat example](https://github.com/facebook/flux/tree/master/examples/flux-chat) with [Scala.js](http://www.scala-js.org/). It uses [scala-js-react](https://github.com/xored/scala-js-react) to be able to use a JSX-like syntax

I tried to be as close as possible of the orginal project structure so that it is easy to compare them.

# Try it

## Locally

A simple simple `sbt start` should start the play server on port `9000`

## On Heroku

[![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

# Remarks, things to improve
 
* I am not too happy with the fact that I have to explicitly describe how my models should map to JSON. I tried to use [scala-js-pickling](https://github.com/scala-js/scala-js-pickling), it worked well but the JSON generated is quite scala specific (it embeds types information in the JSON) which is not great if you plan to have some real JS using the JSON. If you know any solution that would allow me to automatically transform simple case classes into simple JSON let me know.
* Store objects are registred to the dispatcher inside the store object file which means that in Scala the object have to be used somewhere before it is registred (else the "JVM" won't load the file and the registration won't happen) while in JS requiring the file is enough. To solve that I added empty `init` methods in my store objects that I call for all store objects in the main method.Obviously it sucks, so if you know any better way, let me know. I would love to have some kind of annotation `@preload` that I could add on objects that I want the "JVM" to load at startup.
* I had a slug-size problem on Heroku at first because my application was not correctly detected as a play application. To solve it, I just had to create an empty `conf/application.conf` file. Thanks to Heroku support for helping me on this one.
* I used play because it was convenient and I could reuse the SBT config of [play-with-scalajs-example](https://github.com/vmunier/play-with-scalajs-example). It is absolutely not needed as long as you have something else to serve your files
* I used to think that SBT meant Simple Build Tool, my new therory is that it actually means Scala Build Tool ^^
* [scala-js-react](https://github.com/xored/scala-js-react) is really great, Scalax code really looks like JSX.
* [Scala.js](http://www.scala-js.org/) works surprizingly well, there are still missing parts (for example I had to use JS dates to format times because `SimpleDateFormat` was not available) but it is totaly usable. It did not compare performances at all though.
* I used [scala-js-dom]() which is not really required, but since the main avantage to scala.js is to have type safety, I think it's a good idea to use it.
