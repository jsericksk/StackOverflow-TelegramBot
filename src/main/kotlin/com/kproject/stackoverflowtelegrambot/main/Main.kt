package com.kproject.stackoverflowtelegrambot

import com.kproject.stackoverflowtelegrambot.commom.DataState
import com.kproject.stackoverflowtelegrambot.data.parser.GoogleSearch
import com.soywiz.klock.jvm.toDate
import dev.inmo.tgbotapi.bot.ktor.telegramBot
import dev.inmo.tgbotapi.extensions.api.send.reply
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithLongPolling
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.onContentMessage
import dev.inmo.tgbotapi.extensions.utils.textContentOrNull
import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.utils.buildEntities

/**
 * The token is in a constant in the Token.kt file that was added to .gitignore for security reasons.
 * Keep your token safe!
 */
private const val BotToken = token

private const val StartGuide = "Hi! With this bot you can do quick research on programming-related questions. " +
        "Just type your question and the search will be done on Google filtering by Stack Overflow topics."
private val HelpGuide = buildEntities(" ") {
    +"Type your question and the bot will do a Google search. Try to use clear keywords." +
            "Search example: " + bold("get image uri from file android")
}

suspend fun main() {
    val bot = telegramBot(BotToken)
    bot.buildBehaviourWithLongPolling {
        onContentMessage { messageContent ->
            val content = messageContent.content
            val date = messageContent.date.toDate()
            val message = content.textContentOrNull()?.text
            message?.let {
                println("Message: $message")
                println("Date: $date")
                println("".padEnd(110, '-'))

                when (message) {
                    "/start" -> {
                        reply(messageContent, StartGuide)
                    }
                    "/help" -> {
                        reply(messageContent, HelpGuide)
                    }
                    else -> {
                        when (val searchResults = GoogleSearch.getSearchResults(message)) {
                            is DataState.Success -> {
                                searchResults.result?.let { resultList ->
                                    try {
                                        val formattedMessage = GoogleSearch.getFormattedResultList(resultList)
                                        reply(messageContent, formattedMessage)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                }
                            }
                            is DataState.Error -> {
                                reply(messageContent, searchResults.message)
                            }
                        }
                    }
                }
            }
        }
    }.join()
}