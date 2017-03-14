package org.scambient

import scala.collection.JavaConversions._
import scala.collection.mutable

//
//import java.io.File
//import java.lang.reflect.Array
//import java.util
//
//import play.api.libs.json.Reads
//import play.libs.Json
//
//
//case class Station(lat: String, lon: String, name: String, x: String)
//
//case class Flat(
//                 epc_cur: String,
//                 epc_cur_val: Int,
//                 epc_max: String,
//                 epc_max_val: Int,
//                 floorplan: String,
//                 href: String,
//                 id: Int,
//                 image: String,
//                 lat: Float,
//                 lon: Float,
//                 name: String,
//                 price: Int,
//                 sqm: Int,
//                 //                   stations: Array[Station],
//                 tax: Int,
//                 tax_band: String
//               )
//
//object Main {
//  implicit val modelFormat: Reads[Flat] = Json.reads[Flat]
//
//
//  def getFiles(f: File): Stream[File] =
//    f #:: (if (f.isDirectory) f.listFiles().toStream.flatMap(getFiles)
//    else Stream.empty)
//
//
//  def main(args: Array[String]) = {
//    val jsonFiles = getFiles(new File("c:\\csv"))
//      .filter(_.getName.endsWith("json"))
//
//
//    val flats = jsonFiles
//      .map(scala.io.Source.fromFile(_).getLines().mkString)
//      .map(Json.parse)
//      .map(x => {
//        println(x.getClass)
//        Json.fromJson[Flat](x)
//
//      })
//
//    flats.foreach(println)
//
//    //    println(f)
//
//  }
//}
//

case class JobSpec(salary: String, location: String, description: String)




object Main {
  def main(args: Array[String]): Unit = {
    //    println(parsePage())
    val keyword = "developer"
    val radius = 20
    val location = "London"
    val jobType = "Permanent"

    val url = s"http://www.jobsite.co.uk/vacancies?search_type=advanced&engine=stepmatch&search_referer=internal&query=$keyword&logic=any&Location=$location&radius=$radius&title_query=&title_logic=any&vacancy_type=$jobType&search_currency_code=GBP&salary_type_unit=A&salary_min=&salary_max=&sector=IT&daysback=A&latlong=51.5019,-0.126343"
    val doc = org.jsoup.Jsoup.connect(url).get()
    val numberOfJobs = doc.select("#searches > div > strong:nth-child(2)").text().toInt

    (1 to numberOfJobs).foreach(p => parseJobPage(url + s"&p=$p"))
    parseJobPage(url)
  }

  def parseJobPage(url: String): mutable.Buffer[String] = {
    println(url)
    val doc = org.jsoup.Jsoup.connect(url).get()
    val numberOfJobs = doc.select("#searches > div > strong:nth-child(2)")

    val elems = doc.select("div.vacRow:nth-child(2n+1) div h3 a")
    elems.map(_.attr("href"))

  }

  def parsePage(): JobSpec = {
    val doc = org.jsoup.Jsoup.connect("http://www.jobsite.co.uk/job/itsm-developer-957710032?src=search&tmpl=lin&sctr=IT&position=2&page=1&engine=stepmatch&search_referer=internal").get()
    val salary = doc.select("div.summary span.Salary").text()
    val location = doc.select("div.summary span.locationConcat").text()
    val description = doc.select("div.vacancySection div.Description").text()
    JobSpec(salary, location, description)
  }
}


package me.foat.crawler

import scala.language.postfixOps


