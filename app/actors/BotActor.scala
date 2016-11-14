package actors

import javax.inject.Inject

import akka.actor.Actor
import com.microsoft.bot.framework._
import com.microsoft.bot.framework.services._
import play.api.Configuration
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.concurrent.duration._

class BotActor @Inject()(conf: Configuration, authService: AuthenticationAPI) extends Actor {

  private val logger = play.Logger.of(getClass)
  private var authentication = mutable.Map.empty[String, AuthenticationAPI.Token]

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
    case clientName: String =>
      sender() ! authentication.get(clientName)

    case _ =>
      sender() ! false
  }
}
