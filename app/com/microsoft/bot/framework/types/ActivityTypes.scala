package com.microsoft.bot.framework.types

sealed class ActivityTypes(val name: String)

object ActivityTypes {

  /**
    * Message from a user -> bot or bot -> User
    */
  object Message extends ActivityTypes("message")

  /**
    * Bot added removed to contact list
    */
  object ContactRelationUpdate extends ActivityTypes("contactRelationUpdate")

  /**
    * This notification is sent when the conversation's properties change,
    * for example the topic name, or when user joins or leaves the group.
    */
  object ConversationUpdate extends ActivityTypes("conversationUpdate")

  /**
    * a user is typing
    */
  object Typing extends ActivityTypes("typing")

  /**
    * Bounce a message off of the server without replying or changing it's state
    */
  object Ping extends ActivityTypes("ping")

  /**
    * End a conversation
    */
  object EndOfConversation extends ActivityTypes("endOfConversation")

  /**
    * External system has triggered
    */
  object Trigger extends ActivityTypes("trigger")

  /**
    * Delete user data
    */
  object DeleteUserData extends ActivityTypes("deleteUserData")

}
