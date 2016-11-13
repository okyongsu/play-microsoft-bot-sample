package actors

import javax.inject.Inject

import akka.actor.Actor
import akka.util.Timeout
import com.microsoft.bot.framework._
import com.microsoft.bot.framework.models._
import com.microsoft.bot.framework.services._
import play.api.Configuration
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsError, JsSuccess, JsValue}

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.concurrent.duration._

object BotActor {

  case class ServiceData(
    clientName: String,
    message: JsValue,
    callback: (AuthenticationAPI.Response, Activity) => Unit
  )

  case class Service(
    clientName: String,
    message: models.Activity,
    callback: (AuthenticationAPI.Response, Activity) => Unit
  )
}

class BotActor @Inject()(conf: Configuration, authService: AuthenticationAPI)
  extends Actor {

  import BotActor._

  private val logger = play.Logger.of(getClass)

  private var authentication = mutable.Map.empty[String, AuthenticationAPI.Response]

  implicit val timeout: Timeout = 30.seconds

  override def preStart(): Unit = {
    super.preStart()

    conf.getObject(s"$CONFIG.services.authentication.clients").foreach { clients =>
      clients.foreach { case (name, config) =>
        val client = config.atPath(name)
        val id     = client.getString(s"$name.id")
        val secret = client.getString(s"$name.secret")

        context.system.scheduler.schedule(0.seconds, 3000.seconds) {
          authService.getToken(id, secret).foreach { token =>
            token.foreach { t =>
              authentication += name -> t
            }
          }
        }
      }
    }
  }

  def receive = {
    case ServiceData(clientName, json, callback) =>
      json.validate[Activity] match {
        case JsSuccess(activity, _) =>
          logger.info("(Connector -> bot): json")
          self ! Service(clientName, activity, callback)

        case JsError(e) =>
          logger.error("(Connector -> bot): json - error: {}", e)
      }

    case Service(clientName, activity, callback) =>

      val bot = sender()

      authentication.get(clientName) match {
        case Some(auth) => bot ! callback(auth, activity)
        case None       => bot ! "no auth"
      }

    case s: String =>
      sender() ! "Hello, " + s
  }
}
