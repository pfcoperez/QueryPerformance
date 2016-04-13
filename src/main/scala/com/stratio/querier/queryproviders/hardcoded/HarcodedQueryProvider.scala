package com.stratio.querier.queryproviders.hardcoded

import com.stratio.querier.queryproviders.QueryProvider

trait HarcodedQueryProvider extends QueryProvider {

  protected val sqls: Seq[String]

  private final lazy val sqlIt = sqls.iterator
  override final def hasNext: Boolean = sqlIt.hasNext
  override final def next(): String = sqlIt.next
}
