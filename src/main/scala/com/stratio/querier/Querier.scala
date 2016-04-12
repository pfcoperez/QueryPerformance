package com.stratio.querier

import com.stratio.querier.tasks.TestTask
import com.stratio.querier.workers.QueryWorker
import com.stratio.querier.workers.QueryWorker.{QueryRes, WorkerId}

object Querier extends App {

  val worker1 = new QueryWorker {
    override val id: WorkerId = "dummy1"
    override protected def runQuery(sql: String): QueryRes = {
      println("> sql")
      0
    }
  }

  val worker2 = new QueryWorker {
    override val id: WorkerId = "dummy2"
    override protected def runQuery(sql: String): QueryRes = {
      println("# sql")
      Thread.sleep(500)
      1
    }
  }

  val tasks: Seq[TestTask] = Seq(
    TestTask("select * from t", worker1::worker2::Nil),
    TestTask("select c1 from s", worker1::Nil)
  )

  tasks.foreach(t => println(t.run))
}
