package controllers

import javax.inject.{Inject, Named}

import actors.BotActor.ServiceData
import akka.actor.ActorRef
import com.microsoft.bot.framework.models._
import com.microsoft.bot.framework.services._
import com.microsoft.bot.framework.types._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

class BotController @Inject()(@Named("bot-actor") bot: ActorRef, apiConversation: ConversationAPI) extends Controller {

  private val logger = play.Logger.of(getClass)

  /**
    * https://blogs.msdn.microsoft.com/tsmatsuz/2016/08/31/microsoft-bot-framework-messages-howto-image-html-card-button-etc/
    * “We can't show this card on the version of Skype you have.”
    * https://github.com/Microsoft/BotBuilder/issues/720
    * https://github.com/Microsoft/BotBuilder/issues/1518
    * http://stackoverflow.com/questions/38841353/instead-of-displaying-a-receiptcard-skype-client-returns-eek-we-cant-show-th
    */
  def index = Action(parse.json) { implicit request =>

    logger.info("body: {}", Json.prettyPrint(request.body))

    bot ! ServiceData("attendence-bot", request.body, { (auth, activity) =>

      activity.from match {
        case Some(from) if activity.isText =>
          apiConversation.sendTo(auth, from) {
            Json.toJson(
              Activity(
                //`type` = Some(ActivityTypes.Message.name),
                `type` = Some("message/text"),
                summary = Some("summary"),
                //text = Some(s"# Welcome to **${from.name.getOrElse("?")}**\n\nPlease visit [my blog](https://blogs.msdn.microsoft.com/tsmatsuz).\n\n---\n\n${activity.text.getOrElse("?")}")
                text = Some(s"""Welcome to <b>${from.name.getOrElse("?")}</b>. Please visit <a href="https://blogs.msdn.microsoft.com/tsmatsuz">${activity.text.getOrElse("?")}</a> <ss type ="wink">;)</ss>""")
              )
            )
          }
        case _ =>

      }
    })

    Ok
  }

}
