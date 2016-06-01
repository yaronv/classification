package plugins

import akka.actor.Props

object ExtractionFilterPlugin {
  def props(cascadeFilePath: String): Props = Props(classOf[ExtractionFilterPlugin], cascadeFilePath)
}

class ExtractionFilterPlugin(cascadeFilePath: String) extends FilterPlugin {

  def filter(image: Mat): Mat = {
    // use this plugin to perform an extraction of searched objects.
    // For example using a org.opencv.objdetect.CascadeClassifier with a given HAAR/LBP xml file.

    image
  }
}