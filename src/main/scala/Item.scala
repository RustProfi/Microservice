import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

final case class Item(content: String, id: Long)
final case class Order(items: List[Item])

// collect your json format instances into a support trait:
trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val itemFormat = jsonFormat2(Item)
  implicit val orderFormat = jsonFormat1(Order) // contains List[Item]
}