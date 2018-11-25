package io.fgonzaleva.musicforbooks.data.repositories.model

import io.fgonzaleva.musicforbooks.data.api.model.FeedItemResponse
import io.fgonzaleva.musicforbooks.data.cache.model.FeedItemCache

data class BookItem(
    val authorName: String,
    val bookTitle: String,
    val goodReadsId: Int,
    val coverUrl: String
) {

    companion object {
        fun fromFeedItemCache(cache: FeedItemCache): BookItem {
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
    }

}