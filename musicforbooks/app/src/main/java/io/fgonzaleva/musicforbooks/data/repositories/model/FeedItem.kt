package io.fgonzaleva.musicforbooks.data.repositories.model

import io.fgonzaleva.musicforbooks.data.api.model.FeedItemResponse
import io.fgonzaleva.musicforbooks.data.cache.model.FeedItemCache

data class FeedItem(
    val authorName: String,
    val bookTitle: String,
    val goodReadsId: Int,
    val coverUrl: String
) {
    companion object {

        fun fromCache(cache: FeedItemCache): FeedItem {
            return FeedItem(
                cache.authorName,
                cache.bookTitle,
                cache.goodReadsId,
                cache.coverUrl
            )
        }

        fun fromResponse(response: FeedItemResponse): FeedItem {
            return FeedItem(
                response.authorName,
                response.bookTitle,
                response.goodReadsId,
                response.coverUrl
            )
        }

    }
}