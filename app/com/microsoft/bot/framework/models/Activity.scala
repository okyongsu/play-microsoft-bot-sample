package com.microsoft.bot.framework.models

import com.microsoft.bot.framework.types._
import ai.x.play.json.Jsonx

/**
  * Activity
  *
  * @param id Id for the activity
  * @param from Sender address
  * @param conversation Conversation
  * @param timestamp UTC Time when message was sent (Set by service)
  * @param localTimestamp Local time when message was sent (set by client Ex: 2016-09-23T13:07:49.4714686-07:00)
  * @param recipient (Outbound to bot only) Bot's address that received the message
  * @param channelId ChannelId the activity was on
  * @param serviceUrl Service endpoint
  * @param `type` The type of the activity [message|contactRelationUpdate|converationUpdate|typing]
  * @param textFormat Format of text fields [plain|markdown] Default:markdown
  * @param attachmentLayout hint for how to deal with multiple attachments Values: [list|carousel] Default:list
  * @param summary Text to display if you can't render cards
  * @param text Content for the message
  * @param attachments Attachments
  * @param entities Collection of Entity objects, each of which contains metadata about this activity. Each Entity object is typed.
  * @param action ContactAdded/Removed action
  * @param membersAdded Array of address added
  * @param membersRemoved Array of addresses removed
  * @param topicName Conversations new topic name
  * @param historyDisclosed the previous history of the channel was disclosed
  * @param locale The language code of the Text field
  * @param channelData Channel specific payload
  * @param replyToId the original id this message is a response to
  * @param value Open ended value
  */
case class Activity(
  id              : Option[String] = None,
  from            : Option[ChannelAccount] = None,
  conversation    : Option[ConversationAccount] = None,
  timestamp       : Option[String] = None,
  localTimestamp  : Option[String] = None,
  recipient       : Option[ChannelAccount] = None,
  channelId       : Option[String] = None,
  serviceUrl      : Option[String] = None,
  `type`          : Option[String] = None,
  textFormat      : Option[String] = None,
  attachmentLayout: Option[String] = None,
  summary         : Option[String] = None,
  text            : Option[String] = None,
  attachments     : Option[List[Attachment]] = None,
  entities        : Option[List[Entity]] = None,
  action          : Option[String] = None,
  membersAdded    : Option[List[ChannelAccount]] = None,
  membersRemoved  : Option[List[ChannelAccount]] = None,
  topicName       : Option[String] = None,
  historyDisclosed: Option[Boolean] = None,
  locale          : Option[String] = None,
  channelData     : Option[String] = None,
  replyToId       : Option[String] = None,
  value           : Option[String] = None
) {
  import Activity._

  val isContactRelationUpdate = `type`.exists(_.endsWith(ActivityTypes.ContactRelationUpdate.name))

  val isAddAction = isContactRelationUpdate && action.contains(ContactActionTypes.Add.name)
  val isRemoveAction = isContactRelationUpdate && action.contains(ContactActionTypes.Remove.name)

  val isText  = `type`.contains(ActivityTypes.Message.name) && text.nonEmpty
  val isImage = `type`.contains(ActivityTypes.Message.name) && text.isEmpty && attachments.nonEmpty

  val isDeletedText = isText && text.map(removeRichText).map(removeEditMark).contains("")

  val isUpdatedText = isText && text.map(removeRichText).exists { t =>
    t.startsWith(TAG_EDITED_TXT) && s"^$TAG_EDITED_TXT".r.replaceFirstIn(t, "") != ""
  }

  val isAddedText = isText && text.exists { t =>
    !(t.startsWith(TAG_EDITED_TXT) || t.matches(RICH_TXT_BLOCK))
  }

  val textRich = text.map { t =>
  }
}

object Activity {
  def removeEditMark(txt: String) = s"^$TAG_EDITED_TXT".r.replaceFirstIn(txt, "")
  def removeRichText(txt: String) = RICH_TXT_BLOCK.r.replaceFirstIn(txt, "")

  val TAG_EDITED_TXT = "Edited previous message: "
  val RICH_TXT_BLOCK = "<[^>]+/>"

  implicit val json = Jsonx.formatCaseClass[Activity]
}
