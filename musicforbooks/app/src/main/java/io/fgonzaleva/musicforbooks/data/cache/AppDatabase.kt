package io.fgonzaleva.musicforbooks.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import io.fgonzaleva.musicforbooks.data.cache.model.FeedItemCache

@Database(entities = [FeedItemCache::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedCache(): FeedCache
}