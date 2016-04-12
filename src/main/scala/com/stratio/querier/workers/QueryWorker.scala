package com.stratio.querier.workers

import scala.concurrent.duration._

object QueryWorker {
  type QueryRes = Long
  type WorkerId = String
}

trait QueryWorker {
  import QueryWorker._

  val id: WorkerId

  def measureQueryTime(sql: String): (QueryRes, Duration) = {
    val init = System.currentTimeMillis
    (runQuery(sql), (System.currentTimeMillis - init) milliseconds)
  }

  protected def runQuery(sql: String): QueryRes

}
