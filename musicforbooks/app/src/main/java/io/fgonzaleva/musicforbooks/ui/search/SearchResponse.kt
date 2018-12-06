package io.fgonzaleva.musicforbooks.ui.search

import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem

sealed class SearchResponse {

    object Loading : SearchResponse()

    data class Success(
        val results: List<BookItem>
    ): SearchResponse()

    data class Error(
        val error: Throwable
    ): SearchResponse()

}