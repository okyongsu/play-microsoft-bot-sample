package controllers

import com.microsoft.bot.framework.models.Activity
import com.microsoft.bot.framework.services.AuthenticationAPI
import play.api.mvc.{Request, WrappedRequest}

case class BotConnector[A](token: AuthenticationAPI.Token, activity: Activity, request: Request[A])
    extends WrappedRequest[A](request)
