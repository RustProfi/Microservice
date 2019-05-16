import scala.io.StdIn.readLine

object SaveLoadMicroservice {
  val server = new SaveLoad
  def main(args: Array[String]): Unit = {

    println("läuft")
    var input: String = ""
    do {
      input = readLine()
    } while (input != "q")
    server.unbind
  }
}


