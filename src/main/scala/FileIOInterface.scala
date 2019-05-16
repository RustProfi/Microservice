import play.api.libs.json.JsObject

trait FileIOInterface {
  def load: Option[String]

  def loadPlayer: Int

  def save(grid: String): Unit

  def savePlayer(activePlayer: Int): Unit
}
