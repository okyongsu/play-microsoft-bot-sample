package controllers

import javax.inject.{Inject, Named}

import akka.actor.ActorRef
import com.microsoft.bot.framework.models._
import com.microsoft.bot.framework.services._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json

import scala.concurrent.Future

class BotController @Inject()(@Named("bot-actor") bot: ActorRef, apiConversation: ConversationAPI) extends BotAuthentication(bot) {

  def index = BotClient("attendence-bot").async(parse.json) { implicit request =>

    request.activity.from match {
      case Some(from) if request.activity.isText =>
        apiConversation.sendTo(from) {
          Json.toJson(
            Activity(
              `type` = Some("message/text"),
              text   = Some(s"""Welcome to <b>${from.name.getOrElse("?")}</b>. Please visit <a href="https://blogs.msdn.microsoft.com/tsmatsuz">${request.activity.text.getOrElse("?")}</a> <ss type ="wink">;)</ss>""")
            )
          )
        }.collect {
          case true  => Ok
          case false => ServiceUnavailable
        }
      case _ =>
        Future(BadRequest)
    }
  }

}
