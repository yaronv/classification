package plugins

import plugins.Plugin._

/**
 * A FilterPlugin alters an image producing a new image which is then sent to all consumers of this plugin.
 */
trait FilterPlugin extends Plugin {
  def filter(image: Mat): Mat

  def handleImageMessage(image: Image) = image match {
    case Image(mat) =>
      val result = filter(mat)
      consumers foreach (_ ! Image(result))
  }
}