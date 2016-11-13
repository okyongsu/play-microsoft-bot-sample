package com.microsoft.bot.framework.types

sealed class ActionTypes(val name: String)

object ActionTypes {

  /**
    * Client will open given url in the built-in browser.
    */
  object OpenUrl extends ActionTypes("openUrl")

  /**
    * Client will post message to bot,
    * so all other participants will see that was posted to the bot and who posted this.
    */
  object ImBack extends ActionTypes("imBack")

  /**
    * Client will post message to bot privately,
    * so other participants inside conversation will not see that was posted.
    */
  object PostBack extends ActionTypes("postBack")

  /**
    * playback audio container referenced by url
    */
  object PlayAudio extends ActionTypes("playAudio")

  /**
    * playback video container referenced by url
    */
  object PlayVideo extends ActionTypes("playVideo")

  /**
    * show image referenced by url
    */
  object ShowImage extends ActionTypes("showImage")

  /**
    * download file referenced by url
    */
  object DownloadFile extends ActionTypes("downloadFile")

  /**
    * Signin button
    */
  object Signin extends ActionTypes("signin")

}
