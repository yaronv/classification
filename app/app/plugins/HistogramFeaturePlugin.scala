package plugins

import models.Feature

import scala.collection.JavaConversions._

/**
 * This plugin calculates the histogram of an image resulting in rgb mean and rgb standard deviation.
 */
class HistogramFeaturePlugin extends FeaturePlugin {
  def calculate(image: Mat): Seq[Feature] = {
    val bgr: java.util.List[Mat] = new java.util.ArrayList[Mat]

    split(image, bgr)

    val redMean, greenMean, blueMean, redStdDev, greenStdDev, blueStdDev = new MatOfDouble

    meanStdDev(bgr(2), redMean, redStdDev)
    meanStdDev(bgr(1), greenMean, greenStdDev)
    meanStdDev(bgr(0), blueMean, blueStdDev)

    Seq(
      Feature(0, "redMean", redMean.get(0, 0)(0)),
      Feature(1, "greenMean", greenMean.get(0, 0)(0)),
      Feature(2, "blueMean", blueMean.get(0, 0)(0)),
      Feature(3, "redStdDev", redStdDev.get(0, 0)(0)),
      Feature(4, "greenStdDev", greenStdDev.get(0, 0)(0)),
      Feature(5, "blueStdDev", blueStdDev.get(0, 0)(0)))
  }
}