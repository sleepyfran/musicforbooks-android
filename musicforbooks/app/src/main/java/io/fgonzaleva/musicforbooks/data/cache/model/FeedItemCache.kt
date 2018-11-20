package io.fgonzaleva.musicforbooks.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "feedcache")
data class FeedItemCache(
    @PrimaryKey var uid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "author_name") var authorName: String,
    @ColumnInfo(name = "book_title") var bookTitle: String,
    @ColumnInfo(name = "goodreads_id") var goodReadsId: Int,
    @ColumnInfo(name = "cover_url") var coverUrl: String
)