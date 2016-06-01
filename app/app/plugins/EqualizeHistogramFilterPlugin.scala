package plugins

/**
 * This plugin equalizes the histogram of an image.
 */
class EqualizeHistogramFilterPlugin extends FilterPlugin {
  def filter(image: Mat): Mat = {
    val result = new Mat
    equalizeHist(image, result)

    result
  }
}