package plugins

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestKit, TestProbe}
import models.Feature
import org.junit.runner._
import org.specs2.runner._
import org.specs2.specification.Scope
import play.api.test._
import plugins.FeaturePlugin.FeatureResult
import plugins.Plugin._

@RunWith(classOf[JUnitRunner])
class ContoursFeaturePluginSpec extends PlaySpecification {

  class Actors extends TestKit(ActorSystem("test")) with Scope

  "ContoursFilterPlugin" should {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

    "find contours" in new Actors {
      val grey = system.actorOf(Props[GreyScaleFilterPlugin])
      val blur = system.actorOf(Props[BlurFilterPlugin])
      val binary = system.actorOf(Props[ThresholdFilterPlugin])
      val contours = system.actorOf(Props[ContoursFeaturePlugin])
      val image = imread(getClass.getResource("/papilio_demoleus_cut.jpg").getPath)

      val probe = TestProbe()

      grey ! Consumers(Set(blur))
      blur ! Consumers(Set(binary))
      binary ! Consumers(Set(contours))
      contours ! Consumers(Set(probe.ref))

      grey ! Image(image)

      probe.expectMsg(FeatureResult(Seq(
        Feature(6, "segments", 77.0),
        Feature(7, "medianAreaOfSegments", 74.5))))
    }

  }
}
