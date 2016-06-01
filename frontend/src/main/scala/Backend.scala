import actors.{BackendService, FrontendService}
import com.typesafe.scalalogging.LazyLogging

/**
  * Created by yaron on 5/31/16.
  */
object Backend extends App with LazyLogging {

  override def main(args: Array[String]): Unit = {
    // start 2 backend nodes
    BackendService.start(Seq("2551").toArray)
    BackendService.start(Seq("2552").toArray)

    FrontendService.start(Array.empty)
  }
}