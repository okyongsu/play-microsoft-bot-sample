package com.microsoft.bot.framework.services

import javax.inject.Inject

import com.microsoft.bot.framework._
import play.api.Configuration
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.libs.ws.WSClient

import scala.concurrent.Future

object AuthenticationAPI {

  case class Token(
    token_type: String,
    expires_in: Long,
    ext_expires_in: Long,
    access_token: String
  ) {
    def header = Seq("Authorization" -> s"Bearer $access_token")
  }

  object Token {
    implicit val json = Json.format[Token]
  }
}

/**
  * Authentication calls from the bot to the Bot Connector service
  * ---------------------------------------------------------------
  * 1. (Bot -> Connector) Step 1: POST to the MSA/AAD v2 login service to retrieve a token
  * 2. (Bot -> Connector) Step 2: Receive JSON containing JWT token from MSA/AAD v2 login service
  * 3. (Bot -> Connector) Step 3: Send this token to the Bot Connector service
  */
class AuthenticationAPI @Inject()(confBot: Configuration, ws: WSClient) {
  import AuthenticationAPI._

  private val logger = play.Logger.of(getClass)

  def getToken(id: String, secret: String): Future[Option[Token]] =
    confBot.getConfig(s"$CONFIG.services.authentication") match {
      case Some(conf) =>
        for {
          response <- ws.url(conf.getString("url").get).post(
            Map(
              "grant_type"    -> conf.getString("grant_type").toSeq,
              "client_id"     -> Seq(id),
              "client_secret" -> Seq(secret),
              "scope"         -> conf.getString("scope").toSeq
            )
          )
        } yield {
          response.json.validate[Token] match {
            case JsSuccess(r, _) =>
              logger.info("auth!")
              Some(r)

            case JsError(e) =>
              logger.error("json error {}", Json.prettyPrint(response.json))
              None
          }
        }
      case _ =>
        logger.error("not found config: {}", CONFIG)
        Future(None)
    }
}
