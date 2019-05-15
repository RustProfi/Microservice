import akka.actor.ActorSystem
import akka.http.javadsl.server.directives.RouteDirectives
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, StandardRoute}
import akka.stream.ActorMaterializer

class SaveLoad {
  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher


  val groute: Route =
    get {
      pathSingleSlash {
        complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "<h1>Controller</h1>"))
      } ~
        path("controller") {

          gridtoHtml("xD")
        } ~
        path("controller" / "gridSize") {
          gridtoHtml(gridSize.toString)
        } ~
        path("controller" / "createEmptyGrid") {
          createEmptyGrid()
          gridtoHtml(gridToString)
        } ~
        path("controller" / "createNewGrid") {
          createNewGrid()
          gridtoHtml(gridToString)
        } ~
        path("controller" / "save") {
          save()
          gridtoHtml("success")
        } ~
        path("controller" / "load") {
          load()
          gridtoHtml("success")
        } ~
        path("controller" / "score") {
          score()
          gridtoHtml(gridToString)
        } ~
        path("controller" / "gameStatus") {
          gridtoHtml(gameStatus.toString)
        } ~
        path("controller" / "finish") {
          finish()
          gridtoHtml(gridToString)
        } ~
        path("controller" / "gridToString") {
          gridtoHtml(gridToString)
        } ~
        path("controller" / "evaluateGame") {
          gridtoHtml(evaluateGame().toString)
        } ~
        path("controller" / "botState") {
          gridtoHtml(botState().toString)
        } ~
        path("controller" / "enableBot") {
          enableBot()
          gridtoHtml("success")
        } ~
        path("controller" / "disableBot") {
          disableBot()
          gridtoHtml("success")
        } ~
        path("controller" / "bot") {
          bot()
          gridtoHtml("success")
        } ~
        path("controller" / "statusText") {
          gridtoHtml(statusText)
        } ~
        path("controller" / "undo") {
          undo
          gridtoHtml("success")
        } ~
        path("controller" / "redo") {
          redo
          gridtoHtml("success")
        }
    } ~
      put {
        path("controller" / "resize") {
          parameter("newSize".as[Int]) { news =>
            resize(news)
            gridtoHtml(gridToString)
          }

        } ~
          path("controller" / "set") {
            parameter("row".as[Int], "col".as[Int], "value".as[Int]) { (row, col, value) =>
              set(row, col, value)
              gridtoHtml(gridToString)
            }
          } ~
          path("controller" / "cell") {
            parameter("row".as[Int], "col".as[Int]) { (row, col) =>
              gridtoHtml(cell(row, col).toString)
            }
          }
      }

  def gridtoHtml(xd: String): StandardRoute = {
    complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Controller</h1>" + "<pre>"+xd+"</pre>"))
  }

  val bindingFuture = Http().bindAndHandle(groute, "localhost", 8070)
}
