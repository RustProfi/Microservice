import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.{Directives, Route, StandardRoute}
import akka.stream.ActorMaterializer
import spray.json.DefaultJsonProtocol

class SaveLoad extends Directives {
  var fileIo: FileIOInterface = new FileIO

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher


  val route: Route =
    post {
      path("save_grid") {
        entity(as[String]) { content => // will unmarshal JSON to String
          println(content)

          fileIo.save(content)
          complete(content)
        }
      } ~
        path("save_player") {
          entity(as[String]) { content => // will unmarshal JSON to Int
            println(content)
            fileIo.savePlayer(content.toInt)
            complete(content)
          }
        }
    } ~
    get {
      pathSingleSlash {
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "<h1>SaveLoad</h1>"))
      } ~
        path("load_grid") {
          val result = fileIo.load match {
            case Some(grid) => grid
            case None => ""
          }
          complete(result) // will render as JSON
        } ~
        path("load_player") {
          complete(fileIo.loadPlayer.toString) // will render as JSON
        }
    }


  val bindingFuture = Http().bindAndHandle(route, "localhost", 8070)

  def unbind = {
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shut

  }
}
