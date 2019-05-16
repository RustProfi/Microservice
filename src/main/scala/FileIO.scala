import java.io._

import play.api.libs.json._

import scala.io.Source

class FileIO extends FileIOInterface {

  override def load: Option[String] = {
    Option(Source.fromFile("grid.json").getLines.mkString)
  }

  override def save(grid: String): Unit = {
    val pw = new PrintWriter(new File("grid.json"))
    pw.write(Json.prettyPrint(Json.parse(grid)))
    pw.close()
  }

  override def loadPlayer: Int = {
    val source: String = Source.fromFile("player.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val activePlayer = (json \ "activePlayer").get.toString.toInt
    activePlayer
  }

  override def savePlayer(activePlayer: Int): Unit = {
    val pw = new PrintWriter(new File("player.json"))
    pw.write(Json.prettyPrint(playerToJson(activePlayer)))
    pw.close()
  }

  def playerToJson(activePlayer: Int): JsObject = {
    Json.obj("activePlayer" -> JsNumber(activePlayer))
  }

}
