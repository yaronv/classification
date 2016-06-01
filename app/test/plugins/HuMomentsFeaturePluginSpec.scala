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
class HuMomentsFeaturePluginSpec extends PlaySpecification {

  class Actors extends TestKit(ActorSystem("test")) with Scope

  "HuMomentsFilterPlugin" should {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

    "extract all 7 hu moments" in new Actors {
      val grey = system.actorOf(Props[GreyScaleFilterPlugin])
      val blur = system.actorOf(Props[BlurFilterPlugin])
      val hu = system.actorOf(Props[HuMomentsFeaturePlugin])
      val image = imread(getClass.getResource("/papilio_demoleus_cut.jpg").getPath)

      val probe = TestProbe()

      grey ! Consumers(Set(blur))
      blur ! Consumers(Set(hu))
      hu ! Consumers(Set(probe.ref))

      grey ! Image(image)

      probe.expectMsg(FeatureResult(Seq(
        Feature(8, "hu1", 0.7586653709212051),
        Feature(9, "hu2", 0.5294922572208498),
        Feature(10, "hu3", 0.047134844405231836),
        Feature(11, "hu4", 0.0371983418976044),
        Feature(12, "hu5", 0.0015563971379822814),
        Feature(13, "hu6", 0.024583287120933217),
        Feature(14, "hu7", -6.124536255751141E-5))))
    }

  }
}
