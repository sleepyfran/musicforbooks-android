package io.fgonzaleva.musicforbooks.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.fgonzaleva.musicforbooks.data.api.model.musicforbooks.FeedItemResponse
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import java.util.*

@Entity(tableName = "feedcache")
data class FeedCacheItem(
    @PrimaryKey var uid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "author_name") var authorName: String,
    @ColumnInfo(name = "book_title") var bookTitle: String,
    @ColumnInfo(name = "goodreads_id") var goodReadsId: Int,
    @ColumnInfo(name = "cover_url") var coverUrl: String
) {

    companion object {
        fun fromItem(item: BookItem): FeedCacheItem {
            return FeedCacheItem(
                authorName = item.authorName,
                bookTitle = item.bookTitle,
                goodReadsId = item.goodReadsId,
                coverUrl = item.coverUrl
            )
        }

        fun fromResponse(response: FeedItemResponse): FeedCacheItem {
            return FeedCacheItem(
                authorName = response.authorName,
                bookTitle = response.bookTitle,
                goodReadsId = response.goodReadsId,
                coverUrl = response.coverUrl
            )
        }
    }

}