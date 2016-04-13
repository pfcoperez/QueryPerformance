package com.stratio.querier.workers
import com.stratio.querier.workers.QueryWorker.{QueryRes, WorkerId}

import scala.util.Try
import java.sql.DriverManager

class PrestoWorker(host: String, port: Int, catalog: String, schema: String) extends QueryWorker {
  override val id: WorkerId = "PrestoDB"

  private val uri = s"jdbc:presto://$host:$port/$catalog/$schema"
  private val con = DriverManager.getConnection(uri, "test", null)

  override protected def runQuery(sql: String): Try[QueryRes] = {
    val st = con.createStatement
    val res = Try(st.executeQuery(sql)).map(rset => if(rset.last) rset.getRow else 0L)
    st.close
    res
  }
}
