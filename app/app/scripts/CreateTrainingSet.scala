package scripts

import java.io.File
import java.nio.file.{Files, Paths}

import akka.actor.{ActorSystem, Props}
import akka.pattern.ask
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import props.Params
import services.{FilePaths, TrainingSetService}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

/**
  * Created by yaron on 5/29/16.
  */
object CreateTrainingSet extends App with LazyLogging {

  override def main(args: Array[String]): Unit = {

    val directory = new File("../trainingset")

    val port = if (args.isEmpty) "0" else args(0)

    val files = directory.listFiles filter (_.isFile) map (_.getAbsolutePath)

    val conf = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port")
      .withFallback(ConfigFactory.load("application.conf"))

    val system = ActorSystem(Params.CLUSTER_NAME, conf)

    val trainingSetService = system.actorOf(Props[TrainingSetService], "trainingSet")


    val future: Future[String] = ask(trainingSetService, FilePaths(files)).mapTo[String]
    val result = Await.result(future, 1 second)

    logger.info(result)

    // create a new arff classification file from existing manually classified images.
    Files.write(Paths.get("./conf/trainingset.arff"), result.toString.getBytes);
  }
}
