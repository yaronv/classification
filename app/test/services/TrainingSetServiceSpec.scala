package services

import java.io.File
import java.nio.file.{Files, Paths}

import akka.actor.{ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.junit.runner._
import org.specs2.runner._
import org.specs2.specification.Scope
import play.api.test._

import scala.concurrent.duration._

/**
 * This spec can be used as a training set generator using a directory full of already classified images (the file name is the class).
 *
 * The trainingset
 */
@RunWith(classOf[JUnitRunner])
class TrainingSetServiceSpec extends PlaySpecification {

  val directory = new File("../trainingset") // the directory containing all the classified images of the training set

  class Actors extends TestKit(ActorSystem("test")) with Scope with ImplicitSender

  "FeatureVectorCalculator" should {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

    "create arff format out of images" in new Actors {
      val files = directory.listFiles filter (_.isFile) map (_.getAbsolutePath)
      val trainingSetService = system.actorOf(Props[TrainingSetService], "trainingSet")

      trainingSetService ! FilePaths(files)

      val result = receiveOne(30 seconds)

      // create a new arff classification file from existing manually classified images.
      Files.write(Paths.get("./conf/trainingset.arff"), result.toString.getBytes);

      result must not be empty
    }.pendingUntilFixed("unlock to create a new trainigset arff file")

  }
}
