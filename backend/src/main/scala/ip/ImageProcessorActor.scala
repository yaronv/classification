package ip

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

import akka.actor.Actor
import akka.cluster.Cluster
import akka.cluster.ClusterEvent._
import com.typesafe.scalalogging.LazyLogging

/**
  * Created by yaron on 21/04/16.
  */

class ImageProcessorActor extends Actor with LazyLogging {

  val cluster = Cluster(context.system)

  // subscribe to cluster changes, re-subscribe when restart
  override def preStart(): Unit = {
    //#subscribe
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
      classOf[MemberEvent], classOf[UnreachableMember])
  }
  override def postStop(): Unit = cluster.unsubscribe(self)

  override def receive: Receive = {
    case "start" => start()
    case MemberUp(member) =>
      logger.info("Member is Up: {}", member.address)
    case UnreachableMember(member) =>
      logger.info("Member detected as unreachable: {}", member)
    case MemberRemoved(member, previousStatus) =>
      logger.info("Member is Removed: {} after {}",
        member.address, previousStatus)
    case _ => println("Unknown command")
  }

  private def start() = {
    logger.info("image processor started")

    val input: String = "/home/yaron/SampleImages/img.jpg"
    val output: String = "/home/yaron/SampleImages/test.jpg"

    readAndProcess(input, output)

    logger.info("image processor ended")

    context.stop(self)

    context.system.shutdown()
  }

  private def processImpl(img: BufferedImage): BufferedImage = {
    // obtain width and height of image
    val w = img.getWidth
    val h = img.getHeight

    // create new image of the same size
    val out = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB)

    // copy pixels (mirror horizontally)
    for (x <- 0 until w)
      for (y <- 0 until h)
        out.setRGB(x, y, img.getRGB(w - x - 1, y) & 0xffffff)

    // draw red diagonal line
    for (x <- 0 until (h min w))
      out.setRGB(x, x, 0xff0000)

    out
  }

  private def readAndProcess(source: String, dest: String) {
    // read original image, and obtain width and height
    val photo1 = ImageIO.read(new File(source))

    val photo2 = processImpl(photo1)

    // save image to file
    ImageIO.write(photo2, "jpg", new File(dest))
  }
}
