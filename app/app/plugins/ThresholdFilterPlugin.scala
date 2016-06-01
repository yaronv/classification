package plugins

/**
 * The threshold plugin transforms the given image in a binary image.
 */
class ThresholdFilterPlugin extends FilterPlugin {
  def filter(image: Mat): Mat = {
    val result = new Mat
    threshold(image, result, 180, 255, THRESH_BINARY)

    result
  }
}