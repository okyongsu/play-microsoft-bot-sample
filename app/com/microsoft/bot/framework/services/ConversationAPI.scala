package com.microsoft.bot.framework.services

import javax.inject.Inject

import com.microsoft.bot.framework.models._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsValue, Json}
import play.api.libs.ws.WSClient
import play.api.{Configuration, http}

import scala.concurrent.Future

class ConversationAPI @Inject()(confBot: Configuration, ws: WSClient) {

  private val logger = play.Logger.of(getClass)

  def create = ???

  def sendTo(auth: AuthenticationAPI.Response, account: ChannelAccount)(body: => JsValue): Future[Boolean] = {
    logger.info("{} {} {}", auth, account, Json.prettyPrint(body))
    ws.url(s"$url/v3/conversations/${account.id}/activities")
      .withHeaders(auth.header: _*)
      .post(body).map {
        case response if response.status == http.Status.CREATED =>
          logger.info("{}", Json.prettyPrint(response.json))
          true
        case response =>
          logger.error("{}", Json.prettyPrint(response.json))
          false
      }
  }
}
