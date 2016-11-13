package com.microsoft.bot.framework.models

import play.api.libs.json.Json

/**
  * Entity
  * @param mentioned
  * @param text
  * @param `type` Entity Type (typically from schema.org types)
  */
case class Entity(
  mentioned: ChannelAccount,
  text     : String,
  `type`   : String
)

object Entity {
  implicit val json = Json.format[Entity]
}