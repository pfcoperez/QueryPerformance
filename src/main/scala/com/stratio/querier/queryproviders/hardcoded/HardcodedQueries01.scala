package com.stratio.querier.queryproviders.hardcoded

class HardcodedQueries01 extends HardcodedQueryProvider {
  override protected val sqls: Seq[String] = Seq(
    "select * from web_salesa limit 1"
  )
}
