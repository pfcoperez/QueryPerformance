package com.stratio.querier.tasks

import com.stratio.querier.queryproviders.QueryProvider
import com.stratio.querier.workers.QueryWorker

object TaskFactory {
  def apply(queries: QueryProvider, workers: Seq[QueryWorker]): Iterator[TestTask] = queries map {
    case query => TestTask(query, workers)
  }

}
