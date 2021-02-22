import scala.collection._
import scala.io._
import java.io._

case class Employee(name:String, age:Int){
  private var lastName="";
  def setLastName(newValue: String) ={
    lastName = newValue;
  }
  def getLastName: String = lastName;
}


class EmployeeTwo{
  private var name = "";
  private var age = 0;
  def set(name: String, age: Int) = {
    this.name = name;
    this.age = age;
  }

  override def toString(): String = name + " " + age
}

class EmployeeThree(name:String, age:Int){
  require (age > 0)
  require (name!="")
  var newName = name;
  override def toString = newName + " " + age
  def changeName(someName: String): Unit = {
    newName = someName
  }
  def this(name:String) = {
    this(name,1)
  }
}

case class EmployeeFour(fname: String, lname: String, rate: Double){
  def calculate_wage(days: Int):Double = rate * days;
}

class SalesRep(fname: String,lname: String, rate: Double, commission: Double) extends
                                                              EmployeeFour(fname, lname, rate){
  override def calculate_wage(days:Int):Double = super.calculate_wage(days)+commission;
}

trait EmployeeFive{
  override def toString():String = "I am Employee";
}
class SalesRepTwo(fname: String, lname: String)
  extends EmployeeFive {
  override def toString:String = super.toString + " or just a sales rep";
}



object EmployeeTwo {
  private var totalCount = 0
  def inc(): Unit = {
    totalCount += 1
  }
  def getTotalCount: Int = {
    totalCount
  }
}

object App {

  def max(x: Int, y: Int): Int = {
    if(x>y){
      x
    } else {
      y
    }
  }

  def printMe: Unit = println("500")

  def sum(a: Int*): Int = {
    a.reduce({(x, y) => x+y})
    // a.sum, this is the shorthand
  }

  def mySort[T: Ordering](elements: Set[T]): Set[T] = {
    val tree = new mutable.TreeSet[T]
    tree ++= elements
    return tree
  }

  def matchTest(x: Int): String = x match {
    case 1 => "one"
    case 2 => "two"
    case _ => "many"
  }

  def main(args: Array[String]):Unit = {
    var x = 1
    var y = 2
    var z = max(x, y)
    println(z)
    printMe
    println(sum(2, 3, 4, 5, 6))

    val name = "John"
    println(s"Hello $name")

    val fruits = List("apples", "oranges", "pears")
    // fruits.foreach(x => println(x))

    val fruitsConcat = "mango"::"pineapple"::Nil
    //fruitsConcat.foreach(println)
    val moreFruit = "strawberry"::fruitsConcat
    val allFruit = fruits:::moreFruit
    //allFruit.foreach(println)

    allFruit.dropRight(1).foreach(println) // dropRight drops from rightmost, drop does leftmost
    println(allFruit.head, allFruit.tail)
    println(allFruit.isEmpty)
    println(allFruit.sortWith((a,b) => a < b)) // sort in alphabetical order
    println(allFruit.mkString(", "))
    // println(allFruit.exists(s => s == "apples"))
    println(allFruit.contains("tomatoes")) // shorthand for the above

    val veggies = new Array[String](3)
    // var veggies = Array("tomatoes", "cucumbers", "broccoli")
    veggies(0) = "tomatoes"
    veggies(1) = "cucumbers"
    veggies(2) = "broccoli"
    veggies.foreach(x=>println(x))

    val manyFroots = Array.ofDim[String](n1 = 3, n2 = 3) //creates 3x3 array
    manyFroots(0)(0) = "apples"
    manyFroots(0)(1) = "oranges"
    manyFroots(0)(2) = "pears"
    manyFroots(0).foreach(x=>println(x))

    veggies.map(x=>x+"!").foreach(println)

    val numbers = (1 to 10 by 2).toList
    println(numbers)
    // numbers.map(x=>x+1).filter(x => x%3 == 0).foreach(println)
    numbers.map(_+1).filter(_%3 == 0).foreach(println) // shorthand for above

    val _sum = numbers.fold(0)((total, n) => total + n) // can be replaced w numbers.sum
    println(_sum)

    val names = List("Daniel", "Chris", "Joseph")
    val str = names.reduce((acc, n) => acc + ", " + n)
    println(str)

    val person = ("John", 23, "123 Main")
    println(person._1 + ", age " + person._2 + ", lives at " + person._3)

    val mySet = mutable.Set(1, 2, 3, 4)
    val mySet2 = mutable.Set(2, 5, 6, 8)
    mySet++=mySet2
    // mySet.foreach(println)
    println(mySet.contains(3))
    println(mySet & mySet2)
    println(mySet | mySet2)
    println(mySet diff mySet2)

    mySet -= 2
    println(mySet)

    var set = mutable.TreeSet(1, 2, 3)
    set += 2
    set += 6
    set += 10
    println(set.mkString(", "))

    val sortedSet = mySort(Set(5, 3, 4, 1))
    println(sortedSet.mkString(", "))

    val employeeOrdering = new Ordering[Employee] {
      def compare(a: Employee, b: Employee): Int = {
        return a.age.compareTo(b.age);
      }
    }
    val t = new mutable.TreeSet[Employee]()(employeeOrdering);

    t.add(Employee("John", 20))
    t.add(Employee("Bob", 23))
    t.add(Employee("Mike", 19))
    t.add(Employee("Peter", 25))
    t.foreach(println(_))

    val treasureMap = mutable.Map[Int,String]();
    treasureMap += (1 -> "Go to Island")
    treasureMap += (2 -> "Find the Big X")
    treasureMap += (3-> "Dig")
    println(treasureMap(2)) //prints Find the Big X

    val romanNumbers = Map(1->"I",2->"II",3->"III",4->"IV",5->"V")
    println(romanNumbers(4))

    treasureMap.keys //returns all keys
    treasureMap.values //returns all values

    val p1Ratings = Map(
      "Lady in the Water"-> 3.0,
      "Snakes on a Plane"-> 4.0,
      "You, Me and Dupree"-> 3.5
    )
    p1Ratings.map({case (k,v) => k+","+v}).foreach(println)

    val list = for(i <- 1 to 5) yield i
    println(list)

    val a = Array(1, 2, 3, 4, 5)
    val b = for (e <- a if e > 3) yield e*2
    b.foreach(println)

    val lines = Source.fromFile("test.txt").getLines.toList
    val totalSize = lines.toString().length()
    println(totalSize)
    val maxWidth = lines.map(_.length()).max
    println(maxWidth)
    val longestLine = lines.filter(_.length == maxWidth)
    println(longestLine)

//    val pw = new PrintWriter(new File("output.txt"))
//    lines.foreach(x => pw.write(x+"\r\n"))
//    pw.close

    val e = new EmployeeTwo
    e.set("YeeYee", 23)
    println(e)
    EmployeeTwo.inc()
    EmployeeTwo.inc()
    println(EmployeeTwo.getTotalCount)

    val e3 = new EmployeeThree("Bob")
    println(e3)
    e3.changeName("NotBobAnymore")
    println(e3)

    val e1 = Employee("John", 23)
    println(e1)
    val e2 = e1.copy()
    println(e1 == e2)
    e1.setLastName("dod")
    println(e1.getLastName)
    println(e2.getLastName)
    println(e1 == e2)

    val newEmp = new SalesRep("John", "Babson", 10.0, 10)
    println(newEmp.calculate_wage(10))

    val john = new SalesRepTwo("John", "Babson")
    println(john)

    val _list = List(3, 6, 5)
    val secondElement: List[Int] => Int = {
      case x::y::_ => y
      case x::_ => x
      case _ => 8 //if empty list, return nonsense like 8
    }
    println(secondElement(_list))
    
    println(matchTest(5))
  }
}
