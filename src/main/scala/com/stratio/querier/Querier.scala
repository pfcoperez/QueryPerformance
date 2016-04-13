package com.stratio.querier

import com.stratio.querier.queryproviders.DirectoryQueryProvider
import com.stratio.querier.queryproviders.hardcoded.HardcodedQueries01
import com.stratio.querier.tasks.{TaskFactory, TestTask}
import com.stratio.querier.workers.{PrestoWorker, QueryWorker}
import com.stratio.querier.workers.QueryWorker.{QueryRes, WorkerId}

import scala.util.{Success, Try}

object Querier extends App {

  val worker1 = new QueryWorker {
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
  }

  val workers = Seq(new PrestoWorker("10.200.0.118", 1234, "cassandra", "tpcds")) //worker1::worker2::Nil

  val querySource = new HardcodedQueries01 //new DirectoryQueryProvider("./tests_queries/")

  val tasks = TaskFactory(querySource, workers)

  tasks.foreach(t => println(t.run))
  
}
