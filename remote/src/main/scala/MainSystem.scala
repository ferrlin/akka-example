package remote.sc

import akka.actor._
import com.typesafe.config.ConfigFactory
import scala.io.Source
import java.net.URL
import WordCountMaster._

case class FileToCount(url: String) {
  def countWords = {
    Source.fromURL(new URL(url))
      .getLines.foldRight(0)(_.split(" ").size + _)
  }
}
case class WordCount(url: String, count: Int)
case class StartCounting(urls: Seq[String], numActors: Int)

object MainSystem extends App {
  class MainActor(accumulator: ActorRef) extends Actor {
    def receive: Receive = {
      case "start" ⇒
        val urls = List("http://www.infoq.com/",
          "http://www.dzone.com/links/index.html",
          "http://www.manning.com/",
          "http://www.reddit.com/")
        accumulator ! StartCounting(urls, 2)
    }
  }

  // let's start
  run()

  private def run() = {
    val mainSystem = ActorSystem("main", ConfigFactory.load.getConfig("mainsystem"))
    val accumulator = mainSystem.actorOf(WordCountMaster.props, name = "wordCountMaster")
    val m = mainSystem.actorOf(Props(new MainActor(accumulator)))
    m ! "start"
  }
}