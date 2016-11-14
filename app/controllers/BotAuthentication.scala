package controllers

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import com.microsoft.bot.framework.models.Activity
import com.microsoft.bot.framework.services.AuthenticationAPI
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._

import scala.concurrent.Future
import scala.concurrent.duration._

class BotAuthentication(bot: ActorRef) extends Controller {

  class BotClient(name: String) extends ActionBuilder[BotConnector] with ActionRefiner[Request, BotConnector] {

    private val logger = play.Logger.of(getClass)

    implicit val timeout: Timeout = 5.seconds

    def refine[A](request: Request[A]): Future[Either[Result, BotConnector[A]]] =
      (bot ? name).mapTo[Option[AuthenticationAPI.Token]].map { token =>

        // TODO: A is JSON type
        val body = request.body.asInstanceOf[JsValue]

        logger.info("body: {}", Json.prettyPrint(body))

        body.validate[Activity].map { activity =>
          token.map(BotConnector(_, activity, request)).toRight(Unauthorized)
        }.getOrElse(Left(BadRequest))
      }
  }

  object BotClient {
    def apply(name: String) = new BotClient(name)
  }

}
