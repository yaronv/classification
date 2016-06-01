package plugins

/**
 * The blur plugin creates a blurred copy of the image received.
 */
class BlurFilterPlugin extends FilterPlugin {
  def filter(image: Mat): Mat = {
    val result = new Mat
    blur(image, result, new Size(5, 5))

    result
  }
}