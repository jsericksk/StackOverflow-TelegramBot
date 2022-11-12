package com.kproject.stackoverflowtelegrambot.data.parser

import com.kproject.stackoverflowtelegrambot.commom.DataState
import com.kproject.stackoverflowtelegrambot.data.model.SearchResult
import dev.inmo.tgbotapi.types.message.textsources.TextSourcesList
import dev.inmo.tgbotapi.types.message.textsources.bold
import dev.inmo.tgbotapi.types.message.textsources.italic
import dev.inmo.tgbotapi.utils.buildEntities
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.net.URLEncoder

object GoogleSearch {
    private const val SearchErrorUrl = "https://support.google.com/websearch/answer/134479"

    suspend fun getSearchResults(question: String, resultLimit: Int = 5): DataState<List<SearchResult>> {
        return try {
            withContext(Dispatchers.IO) {
                val formattedQuestion = URLEncoder.encode(question, "utf-8")
                val googleSearchUrl = "https://google.com/search?q=$formattedQuestion+site:stackoverflow.com"
                val document = Jsoup.connect(googleSearchUrl).timeout(15000).get()
                val elements = document.getElementsByClass("eqAnXb").select(".MjjYud")
                val resultsList = mutableListOf<SearchResult>()
                var currentResultIndex = 0
                for (element in elements) {
                    val title = element.getElementsByClass("LC20lb MBeuO DKV0Md").text()
                    val topicUrl = element.getElementsByTag("a").attr("href")
                    val moreInfo = element.getElementsByClass("Z26q7c UK95Uc").first()?.text()
                    if (topicUrl.contains(SearchErrorUrl)) {
                        return@withContext DataState.Error(
                            "The search did not return any results. Try searching with clearer keywords."
                        )
                    }
                    val searchResult = SearchResult(
                        title = title,
                        topicUrl = topicUrl,
                        moreInfo = moreInfo
                    )
                    if (currentResultIndex <= resultLimit) {
                        resultsList.add(searchResult)
                        currentResultIndex++
                    } else {
                        break
                    }
                }

                DataState.Success(resultsList)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return DataState.Error("Sorry. There was an error doing the search.")
        }
    }

    fun getFormattedResultList(resultsList: List<SearchResult>): TextSourcesList {
        return buildEntities("\n") {
            +bold("Search results:")
            +bold("".padEnd(100, '-'))
            resultsList.forEach { searchResult ->
                +bold(searchResult.title)
                +italic(searchResult.topicUrl)
                +italic("${searchResult.moreInfo}")
                +bold("".padEnd(100, '-'))
            }
        }
    }
}