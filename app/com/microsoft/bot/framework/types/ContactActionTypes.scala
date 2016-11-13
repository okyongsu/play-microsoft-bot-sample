package com.microsoft.bot.framework.types

sealed class ContactActionTypes(val name: String)

object ContactActionTypes {

  /**
    * Bot added to user contacts
    */
  object Add extends ContactActionTypes("add")

  /**
    * Bot removed from user contacts
    */
  object Remove extends ContactActionTypes("remove")

}
