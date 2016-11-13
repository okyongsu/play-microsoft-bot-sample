package com.microsoft.bot.framework.types

sealed class TextFormatTypes(val name: String)

object TextFormatTypes {

  /**
    * Default- interpret text fields as markdown
    */
  object Markdown extends TextFormatTypes("markdown")

  /**
    * Plain text (do not interpret as anything)
    */
  object Plain extends TextFormatTypes("plain")

  /**
    * B, I, S, U, A NOTE: Only supported on Skype for now
    */
  object Xml extends TextFormatTypes("xml")
}
