package com.stratio.querier.workers
import com.stratio.crossdata.common.result.{ErrorSQLResult, SuccessfulSQLResult}
import com.stratio.crossdata.driver.Driver
import com.stratio.crossdata.driver.config.DriverConf
import com.stratio.querier.workers.QueryWorker.{QueryRes, WorkerId}
import org.apache.hadoop.hdfs.DFSClient.Conf

import scala.concurrent.duration.Duration
import scala.util.{Failure, Success, Try}


class CrossdataDriverWorker(
                             desc: String,
                             conf: Option[DriverConf] = None,
                             timeout: Duration = Duration.Inf
                           ) extends QueryWorker {

  override val id: WorkerId = s"CrossdataDriver_$desc"

  private val driver = conf.map(Driver.getOrCreate).getOrElse(Driver.getOrCreate)

  override protected def runQuery(sql: String): Try[QueryRes] =
    driver.sql(sql).waitForResult(timeout) match {
      case ErrorSQLResult(msg, cause) => Failure(cause.getOrElse(new RuntimeException(msg)))
      case SuccessfulSQLResult(rset, _) => Success(rset.length)
    }

}
