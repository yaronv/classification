import com.typesafe.scalalogging.LazyLogging

/**
  * Created by yaron on 21/04/16.
  */
object Main extends App with LazyLogging {

  override def main(args: Array[String]) {

//    logger.info("Application started")
//
//    if (args.isEmpty)
//      startup(Seq("2551", "2552", "0"))
//    else
//      startup(args)
//
//
//    logger.info("Akka system is running")
//  }
//
//  def startup(ports: Seq[String]): Unit = {
//    ports foreach { port =>
//      // Override the configuration of the port
//      val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + port).
//        withFallback(ConfigFactory.load())
//
//      // Create an Akka system
//      val system = ActorSystem(Params.CLUSTER_NAME, config)
//      // Create an actor that handles cluster domain events
//      system.actorOf(Props[ClusterListener], name = "clusterListener")
//    }
  }

}
