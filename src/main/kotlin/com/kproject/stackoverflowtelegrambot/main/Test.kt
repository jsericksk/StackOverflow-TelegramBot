package com.kproject.stackoverflowtelegrambot.main

import com.kproject.stackoverflowtelegrambot.commom.DataState
import com.kproject.stackoverflowtelegrambot.data.parser.GoogleSearch

suspend fun main() {
    when (val results = GoogleSearch.getSearchResults("get image uri from file android")) {
        is DataState.Success -> {
            println(results.result)
        }
        is DataState.Error -> {
            println(results.message)
        }
    }
}