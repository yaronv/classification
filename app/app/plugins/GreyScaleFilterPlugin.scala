package plugins

/**
 * This plugin converts an image into grayscale mode.
 */
class GreyScaleFilterPlugin extends FilterPlugin {
  def filter(image: Mat): Mat = {
    val greyImage = new Mat
    cvtColor(image, greyImage, COLOR_BGR2GRAY)

    greyImage
  }
}