package com.stratio.querier.queryproviders

import java.io.File
import scala.io.Source.fromFile

class DirectoryQueryProvider(val path: String) extends QueryProvider {

  private val ifiles: Iterator[File] = {
    val df: File = new File(path)
    require(df.exists && df.isDirectory)
    df.listFiles.filter(_.canRead).iterator
  }

  override def hasNext: Boolean = ifiles.hasNext
  override def next(): String = fromFile(ifiles.next, "utf-8").getLines mkString "\n"

}
