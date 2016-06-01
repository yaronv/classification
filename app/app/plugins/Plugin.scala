package plugins

import akka.actor.{Actor, ActorRef}
import plugins.Plugin._

object Plugin {
  case class Image(image: Mat)

  case class Consumers(consumers: Set[ActorRef])
}

/**
 * A plugin is an actor which receives Consumers or Image messages.
 * The handling of the Image messages is implemented in the concrete subclasses.
 */
trait Plugin extends Actor {
  var consumers = Set.empty[ActorRef]

  def handleImageMessage(image: Image)

  def receive = {
    case Consumers(cs) =>
      cs foreach { c =>
        if (c != self) {
          if (!consumers.contains(c)) {
            consumers += c
          }
        }
      }

    case image @ Image(mat) =>
      handleImageMessage(image)
  }
}