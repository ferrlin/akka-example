package countwords

import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

object WorkerSystem extends App {
  val workerSystem = ActorSystem("workersystem", ConfigFactory.load.getConfig("workersystem"))
}