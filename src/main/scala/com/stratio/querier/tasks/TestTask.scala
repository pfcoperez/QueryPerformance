package com.stratio.querier.tasks

import com.stratio.querier.tasks.TestTask.TaskResult
import com.stratio.querier.workers.QueryWorker
import com.stratio.querier.workers.QueryWorker.{QueryRes, WorkerId}

import scala.concurrent.duration.Duration

object TestTask {

  case class TaskResult(query: String, res: Seq[(WorkerId, QueryRes, Duration)]) {

    import com.stratio.querier.tasks.serialization.TaskResultSerializer
    import org.json4s.jackson.Serialization.writePretty
    import org.json4s.DefaultFormats
    implicit val _ = DefaultFormats + TaskResultSerializer

    override def toString: String = writePretty(this)

    /*override def toString: String = {
      s"""
         |$query
         |${"-"*query.length}
         |
       """.stripMargin + (res.map { case (id, res, dur) => s"$id: $res in $dur"} mkString "\t")
    }*/
  }

}

case class TestTask(query: String, workers: Seq[QueryWorker]) {
  def run: TaskResult =
    TaskResult(
      query,
      for(worker <- workers; (res, duration) = worker.measureQueryTime(query)) yield (worker.id, res, duration)
    )
}