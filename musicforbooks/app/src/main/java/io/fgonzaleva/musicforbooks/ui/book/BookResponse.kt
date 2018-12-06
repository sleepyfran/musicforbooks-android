package io.fgonzaleva.musicforbooks.ui.book

import io.fgonzaleva.musicforbooks.data.repositories.model.Book

sealed class BookResponse {

    object Loading : BookResponse()

    data class Success(
        val book: Book
    ): BookResponse()

    data class Error(
        val error: Throwable
    ): BookResponse()

}