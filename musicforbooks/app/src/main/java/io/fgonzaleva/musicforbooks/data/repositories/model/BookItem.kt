package io.fgonzaleva.musicforbooks.data.repositories.model

import io.fgonzaleva.musicforbooks.data.api.model.goodreads.BookResponse
import io.fgonzaleva.musicforbooks.data.api.model.musicforbooks.FeedItemResponse
import io.fgonzaleva.musicforbooks.data.api.model.goodreads.BookResultResponse
import io.fgonzaleva.musicforbooks.data.cache.model.FeedCacheItem

data class BookItem(
    val authorName: String,
    val bookTitle: String,
    val goodReadsId: Int,
    val coverUrl: String
) {

    companion object {
        fun fromFeedItemCache(cache: FeedCacheItem): BookItem {
            return BookItem(
                cache.authorName,
                cache.bookTitle,
                cache.goodReadsId,
                cache.coverUrl
            )
        }

        fun fromFeedItemResponse(response: FeedItemResponse): BookItem {
            return BookItem(
                response.authorName,
                response.bookTitle,
                response.goodReadsId,
                response.coverUrl
            )
        }

        fun fromBookResultResponse(response: BookResultResponse): BookItem {
            return BookItem(
                response.authorName,
                response.title,
                response.goodReadsId,
                response.imageUrl
            )
        }

        fun fromBookResponse(response: BookResponse): BookItem {
            return BookItem(
                response.authors.first().name,
                response.title,
                response.id,
                response.imageUrl
            )
        }
    }

}