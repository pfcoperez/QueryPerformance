package com.stratio.querier

import com.stratio.querier.queryproviders.hardcoded.TPCDSSelection
import com.stratio.querier.tasks.TaskFactory
import com.stratio.querier.workers.{CrossdataDriverWorker, PrestoWorker}

object QuerierCassandraCrossdata extends App {

  val workers = Seq(
    new CrossdataDriverWorker("Casssandra")
  )

  val querySource = TPCDSSelection("tpcds")

  val tasks = TaskFactory(querySource, workers)
  tasks.foreach(t => println(t.run))


}

object PrestoCassandraWorker extends App {

  val workers = Seq(new PrestoWorker("10.200.0.118", 1234, "cassandra", "tpcds")) //worker1::worker2::Nil
  val querySource = TPCDSSelection()

  val tasks = TaskFactory(querySource, workers)
  tasks.foreach(t => println(t.run))

}

/*  val worker1 = new QueryWorker {
    override val id: WorkerId = "dummy1"
    override protected def runQuery(sql: String): Try[QueryRes] = {
      println("> sql")
      Success(0)
    }
  }

  val worker2 = new QueryWorker {
    override val id: WorkerId = "dummy2"
    override protected def runQuery(sql: String): Try[QueryRes] = {
      println("# sql")
      Thread.sleep(500)
      Success(1)
    }
  }*/