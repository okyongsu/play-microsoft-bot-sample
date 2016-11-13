package com.microsoft.bot.framework.models

import play.api.libs.json.Json

/**
  * Attachment
  *
  * @param contentType mimetype/Contenttype for the file
  * @param content Embedded content
  * @param thumbnailUrl Thumbnail associated with attachment
  * @param contentUrl Content Url
  * @param name The name of the attachment
  */
case class Attachment(
  contentType : Option[String] = None,
  content     : Option[String] = None,
  thumbnailUrl: Option[String] = None,
  contentUrl  : Option[String] = None,
  name        : Option[String] = None
)

object Attachment {
  implicit val jsonDown = Json.format[Attachment]
}
