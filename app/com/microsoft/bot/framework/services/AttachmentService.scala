package com.microsoft.bot.framework.services

class AttachmentService {
  def get = ???
  def upload = ???
  /*} else if (activity.isImage) {
              Json.obj(
                "type" -> "message/image",
                "attachments" -> activity.attachments.toList.flatten.map { download =>

                  download.thumbnailUrl
                    .foreach { url =>
                      val wsDownload = ws.url(url)
                        .withHeaders(
                          "Authorization" -> s"Bearer ${r.access_token}"
                        )
                        .get().map { res =>

                        play.Logger.info("thumbnailUrl header {}", res.allHeaders)
                        if (res.status != http.Status.OK) {
                          logger.info("thumbnailUrl download {}", res.body)
                        }

                      }.recover { case e: Exception =>
                        None
                      }

                      Await.result(wsDownload, timeout.duration)
                    }

                  Json.toJson(
                    Attachment(
                      contentType = download.contentType,
                      contentUrl = download.contentUrl
                        .flatMap { url =>
                          val wsDownload = ws.url(url)
                            .withHeaders(
                              "Authorization" -> s"Bearer ${r.access_token}"
                            )
                            .get().map { res =>

                            if (res.status != http.Status.OK) {
                              play.Logger.info("contentUrl download {}", res.body)
                              None
                            } else {
                              Option("data:" + download.contentType.get + ";base64," + new String(Base64.encodeBase64(res.bodyAsBytes.toArray[Byte])))
                            }
                          }.recover { case e: Exception =>
                            None
                          }

                          Await.result(wsDownload, timeout.duration)
                        }
                    )
                  )
                }
              )*/
}
