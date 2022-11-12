package com.kproject.stackoverflowtelegrambot.data.parser

import org.jsoup.Jsoup

object StackOverflow {

    fun getFirstAnswerFromTopic(topicUrl: String): String {
        return try {
            val document = Jsoup.connect(topicUrl).get()
            val element = document.getElementsByClass("answercell post-layout--right").first()
            if (element != null) {
                val firstAnswer = element.getElementsByClass("s-prose js-post-body").text()
                firstAnswer
            } else {
                "There is no answer in this topic."
            }
        } catch (e: Exception) {
            "Sorry. There was an error trying to get the answer from topic."
        }
    }
}