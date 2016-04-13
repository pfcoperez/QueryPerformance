package com.stratio.querier.tasks.serialization

import com.stratio.querier.tasks.TestTask.TaskResult
import com.stratio.querier.workers.QueryWorker._
import org.json4s.CustomSerializer
import org.json4s.JsonAST.JString
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._

import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}

object TaskResultSerializer extends CustomSerializer[TaskResult]( formats =>
  (
    PartialFunction.empty,
    {
      case TaskResult(query, res: Seq[(WorkerId, Try[QueryRes], Duration)]) =>
        ("query" -> query) ~ ( "results" -> res.map { case (wid, qres, dur) =>
          val res = qres match {
            case Success(r) => ("successful" -> true) ~ ("value" -> render(r))
            case Failure(reason) => ("successful" -> false) ~ ("value" -> JString(reason.getMessage))
          }
          ("worker" -> wid) ~ ("result" -> res) ~ ("duration_ms" -> dur.toMillis)
        })
    }
  )
)
