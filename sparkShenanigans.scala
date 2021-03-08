import org.apache.spark.SparkContext._
import scala.io._
import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.spark.rdd._
import org.apache.log4j.Logger
import org.apache.log4j.Level
import scala.collection._

object SparkShenanigans {
  def main(args: Array[String]) {
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)
    val conf = new SparkConf().setAppName("App").setMaster("local[4]")
    val sc = new SparkContext(conf)

    println("-------QUESTION 1--------")
    val stuInfo = sc.textFile("studentInfo.txt").map(x =>
      (x.split(", ")(0), x.split(", ")(1))).persist()
    val classDiff = sc.textFile("classDifficulty.txt").map(x =>
      (x.split(", ")(0), x.split(", ")(1))).persist()
    val stuCourses = sc.textFile("courses.txt").map(x =>
      (x.split(", ")(0), x.split(", ")(1))).persist()

    val stuInfoCourse = stuInfo.join(stuCourses).map(
      {case (_, (stuName, stuCourse)) => (stuCourse, stuName)})
    val stuCourseDifficulty = stuInfoCourse.join(classDiff).map(
      {case (stuCourse, (stuName, classDiff)) => (stuName, stuCourse, classDiff)})

    val hardestClass = stuCourseDifficulty.sortBy(-_._3.toInt).take(1).map(_._2)
    stuCourseDifficulty.filter(_._2 == hardestClass(0)).collect()
      .foreach(x => println(x._1))

    println("-------QUESTION 2--------")
    val _stuInfoCourse = stuInfo.leftOuterJoin(stuCourses).map({
        case (_, (stuName, Some(stuCourse))) => (stuCourse, stuName)
        case(_, (stuName, None)) => (stuName, stuName)
      })
    val _stuCourseDifficulty = _stuInfoCourse.leftOuterJoin(classDiff).map({
      case (_, (stuName, Some(classDiff))) => (stuName, classDiff.toDouble)
      case(_, (stuName, None)) => (stuName, 0.0)
    })
    _stuCourseDifficulty.groupByKey().mapValues(_.toList).collect()
      .foreach(x => println(x._1 + ", " + x._2.sum / x._2.length))

    println("-------QUESTION 3--------")
    classDiff.map(x => (x._2 * -1, x._1)).sortByKey().top(5)
      .foreach(x => println(x._2))

    println("-------QUESTION 4--------")
    val courseGrade = sc.textFile("courses.txt").map(x =>
      (x.split(", ")(0), x.split(", ")(2))).persist()

    val gradeMap = Map("A" -> 4.0, "B" -> 3.0, "C" -> 2.0, "D" -> 1.0, "F" -> 0.0)

    val stuInfoCourseGrade = stuInfo.leftOuterJoin(courseGrade).map({
      case (_, (stuName, Some(stuGrade))) => (stuName, gradeMap(stuGrade))
      case (_, (name, None)) => (name, 0.0)
    })

    stuInfoCourseGrade.groupByKey().mapValues(_.toList).collect()
      .foreach(x => println(x._1 + ", " + x._2.sum / x._2.length))
  }
}

