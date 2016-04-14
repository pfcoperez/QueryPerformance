package com.stratio.querier.workers
import com.stratio.crossdata.common.result.{ErrorSQLResult, SuccessfulSQLResult}
import com.stratio.crossdata.driver.Driver
import com.stratio.querier.workers.QueryWorker.{QueryRes, WorkerId}

import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}


class CrossdataDriverWorker(timeout: Duration = Duration.Inf) extends QueryWorker {
  override val id: WorkerId = "CrossdataDriver"

  private val driver = Driver.getOrCreate

  override protected def runQuery(sql: String): Try[QueryRes] =
    driver.sql(sql).waitForResult(timeout) match {
      case ErrorSQLResult(msg, cause) => Failure(cause.getOrElse(new RuntimeException(msg)))
      case SuccessfulSQLResult(rset, _) => Success(rset.length)
    }

}
