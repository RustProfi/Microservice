import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Directives, Route, StandardRoute}
import akka.stream.ActorMaterializer

class SaveLoad extends Directives with JsonSupport {
  var fileIo: FileIOInterface = new FileIO

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher


  val route: Route =
    get {
      pathSingleSlash {
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "<h1>SaveLoad</h1>"))
      } ~
        path("load") {

          val result = fileIo.load match {
            case Some(grid) => grid
            case None => ""
          }
          complete(Order(List(Item(result, 0), Item(fileIo.loadPlayer.toString, 1)))) // will render as JSON
        }
    }
        put {
          path("save") {
            entity(as[Order]) { order => // will unmarshal JSON to Order
              val itemsCount = order.items.size
              val itemNames = order.items.map(_.content).mkString(", ")
              fileIo.save(order.items.filter(p => p.id == 0).head.content)
              fileIo.savePlayer(order.items.filter(p => p.id == 1).head.content.toInt)
              complete(s"Ordered $itemsCount items: $itemNames")
            }
         }
        }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8070)

  def unbind = {
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shut

  }

}
