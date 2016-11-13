package com.microsoft.bot.framework.models

import play.api.libs.json.Json

/**
  * ChannelAccount
  *
  * @param id Channel id for the user or bot on this channel (Example: joe@smith.com, or @joesmith or 123456)
  * @param name Display friendly name
  */
case class ChannelAccount(
  id  : String,
  name: Option[String]
)

object ChannelAccount {
  implicit val json = Json.format[ChannelAccount]
}