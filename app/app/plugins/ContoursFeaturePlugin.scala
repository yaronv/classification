package plugins

import models.Feature

import scala.collection.JavaConversions._
import scala.util.Random

class ContoursFeaturePlugin extends FeaturePlugin {
  def calculate(image: Mat): Seq[Feature] = {
    val contours: java.util.List[MatOfPoint] = new java.util.ArrayList[MatOfPoint]
    val hierarchy = new Mat
    val contoursImage = Mat.zeros(image.size, CV_8UC3)

    findContours(image, contours, hierarchy, RETR_TREE, CHAIN_APPROX_SIMPLE)

    for (i <- 0 until contours.size) {
      drawContours(contoursImage, contours, i, new Scalar(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255)))
    }

    val n = contours.size

    // could be inefficient for many contours
    val sorted = contours.sortBy(contourArea)
    val medianArea = if (n % 2 != 0) {
      contourArea(sorted((n + 1) / 2))
    } else {
      1 / 2 * contourArea(sorted(n / 2)) + contourArea(sorted(n / 2 + 1))
    }

    Seq(Feature(6, "segments", contours.size), Feature(7, "medianAreaOfSegments", medianArea))
  }
}