package com.microsoft.bot.framework.models

import play.api.libs.json.Json

/**
  * ConversationAccount
  *
  * @param id Channel id for the user or bot on this channel (Example: joe@smith.com, or @joesmith or 123456)
  * @param name Display friendly name
  * @param isGroup Is this a reference to a group
  */
case class ConversationAccount(
  id     : String,
  name   : Option[String],
  isGroup: Option[Boolean]
)

object ConversationAccount {
  implicit val json = Json.format[ConversationAccount]
}
