package com.microsoft.bot.framework.services

import javax.inject.Inject

import com.microsoft.bot.framework.models._
import controllers.BotConnector
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSClient
import play.api.{Configuration, http}

import scala.concurrent.Future

class ConversationAPI @Inject()(confBot: Configuration, ws: WSClient) {

  private val logger = play.Logger.of(getClass)

  def create = ???

  def sendTo(account: ChannelAccount)(body: JsValue)(implicit request: BotConnector[_]): Future[Boolean] = {

    logger.info("{} {} {}", request.token, account, Json.prettyPrint(body))

    ws.url(s"$url/v3/conversations/${account.id}/activities")
      .withHeaders(request.token.header: _*)
      .post(body).map {
        case response if response.status == http.Status.CREATED =>
          logger.info("{}", Json.prettyPrint(response.json))
          true
        case response =>
          logger.error("{}", Json.prettyPrint(response.json))
          false
      }.recover { case e: Exception =>
        logger.error("error", e)
        false
      }
  }
}
