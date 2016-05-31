package actors

import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.pipe
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import props.Params

import scala.annotation.tailrec
import scala.concurrent.Future

/**
  * Created by yaron on 5/31/16.
  */
class BackendService extends Actor with LazyLogging {
  import context.dispatcher

  def receive = {
    case (n: Int) =>
      Future(factorial(n)) map { result => (n, result) } pipeTo sender()
  }

  def factorial(n: Int): BigInt = {
    @tailrec def factorialAcc(acc: BigInt, n: Int): BigInt = {
      if (n <= 1) acc
      else factorialAcc(acc * n, n - 1)
    }
    factorialAcc(BigInt(1), n)
  }
}

object BackendService {

  def start(args: Array[String]): Unit = {
    // Override the configuration of the port when specified as program argument
    val port = if (args.isEmpty) "0" else args(0)
    val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=$port").
      withFallback(ConfigFactory.parseString("akka.cluster.roles = [backend]")).
      withFallback(ConfigFactory.load())

    val system = ActorSystem(Params.CLUSTER_NAME, config)
    system.actorOf(Props[BackendService], name = "backendService")

  }

}

