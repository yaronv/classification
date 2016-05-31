package actors

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorSystem, Props, ReceiveTimeout}
import akka.cluster.Cluster
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import props.Params

import scala.concurrent.duration.Duration

/**
  * Created by yaron on 5/31/16.
  */
class FrontendService(upToN: Int, repeat: Boolean) extends Actor with LazyLogging {
  val backend = context.actorOf(FromConfig.props(),
    name = "backendRouter")

  override def preStart(): Unit = {
    sendJobs()
    if (repeat) {
      context.setReceiveTimeout(Duration.create(10, TimeUnit.SECONDS))
    }
  }

  def receive = {
    case (n: Int, factorial: BigInt) =>
      if (n == upToN) {
        logger.info(s"{$n}! = {$factorial}")
        if (repeat) sendJobs()
        else context.stop(self)
      }
    case ReceiveTimeout =>
      logger.info("Timeout")
      sendJobs()
  }

  def sendJobs(): Unit = {
    logger.info("Starting batch of factorials up to [{}]", upToN)
    1 to upToN foreach { backend ! _ }
  }
}

object FrontendService {
  def start(args: Array[String]): Unit = {
    val upToN = 200

    val config = ConfigFactory.parseString("akka.cluster.roles = [frontend]").
      withFallback(ConfigFactory.load("application.conf"))

    val system = ActorSystem(Params.CLUSTER_NAME, config)
    val cluster = Cluster(system)
    system.log.info("Factorials will start when 2 backend members in the cluster.")
    //#registerOnUp
    cluster registerOnMemberUp {
      system.actorOf(Props(classOf[FrontendService], upToN, false),
        name = "frontendService")
    }
    //#registerOnUp

//    //#registerOnRemoved
//    cluster.registerOnMemberRemoved {
//      // exit JVM when ActorSystem has been terminated
//      system.registerOnTermination(System.exit(0))
//      // shut down ActorSystem
//      system.shutdown()
//
//      // In case ActorSystem shutdown takes longer than 10 seconds,
//      // exit the JVM forcefully anyway.
//      // We must spawn a separate thread to not block current thread,
//      // since that would have blocked the shutdown of the ActorSystem.
//      new Thread {
//        override def run(): Unit = {
//          if (Try(Await.ready(system.whenTerminated, 10.seconds)).isFailure)
//            System.exit(-1)
//        }
//      }.start()
//    }
    //#registerOnRemoved

  }
}