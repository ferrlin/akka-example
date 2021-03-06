package remote.sc

import akka.actor._
import countwords._
import java.io.File
import scala.io.Source
import remote.jv.WordCounterWorker

class WordCountMaster extends Actor with ActorLogging{

  var fileNames: Seq[String] = Nil
  var sortedCount: Seq[(String, Int)] = Nil

  def receive: Receive = {
    case StartCounting(urls, numActors) ⇒
      val workers = createWorkers(numActors)
      fileNames = urls
      beginSorting(fileNames, workers)
    case WordCount(fileName, count) ⇒
      sortedCount = (sortedCount :+ (fileName, count)).sortWith(_._2 < _._2)
      if (sortedCount.size == fileNames.size) {
        // println(s"final result $sortedCount")
        log.info(s"Final result $sortedCount")
        finishSorting()
      }
  }

  private def createWorkers(numActors: Int) =
    for (i ← 0 until numActors) yield context.actorOf(Props[WordCounterWorker], name = s"worker-${i}")

  private[this] def beginSorting(fileNames: Seq[String], workers: Seq[ActorRef]) {
    fileNames.zipWithIndex.foreach(e ⇒ {
      workers(e._2 % workers.size) ! FileToCount(e._1)
    })
  }

  private[this] def finishSorting() {
    context.system.shutdown()
  }
}

object WordCountMaster {

  def props = Props[WordCountMaster]
}