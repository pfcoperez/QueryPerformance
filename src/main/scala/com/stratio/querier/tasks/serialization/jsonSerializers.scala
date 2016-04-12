package com.stratio.querier.tasks.serialization

import com.stratio.querier.tasks.TestTask.TaskResult
import com.stratio.querier.workers.QueryWorker._
import org.json4s.CustomSerializer
import org.json4s.JsonAST._
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._


import scala.concurrent.duration.Duration

object TaskResultSerializer extends CustomSerializer[TaskResult]( formats =>
  (
    PartialFunction.empty,
    {
      case TaskResult(query, res: Seq[(WorkerId, QueryRes, Duration)]) =>
        JObject(
          List(
          JField("query", JString(query)),
            JField("results", JArray(
              res.map {
                case (wid, qres, dur) =>
                  JObject(
                    JField(
                      "worker", JString(wid)
                    )::JField(
                      "result", render(qres)
                    )::JField(
                      "duration_ms", JLong(dur.toMillis)
                    )::Nil
                  )
              } toList
            )
            )
          )
        )
    }
  )
)
