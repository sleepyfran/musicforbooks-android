package io.fgonzaleva.musicforbooks.data.repositories.model

import io.fgonzaleva.musicforbooks.data.api.model.goodreads.BookResponse

data class Book(
    val title: String,
    val authorName: String,
    val coverUrl: String,
    val rating: Float,
    val goodReadsUrl: String
) {

    companion object {
        fun fromBookResponse(bookResponse: BookResponse): Book {
            return Book(
                bookResponse.title,
                bookResponse.authors.first().name,
                bookResponse.imageUrl,
                bookResponse.rating.toFloat(),
                bookResponse.goodReadsUrl
            )
        }
    }

}