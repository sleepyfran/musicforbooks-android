package io.fgonzaleva.musicforbooks.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.fgonzaleva.musicforbooks.data.cache.model.FeedItemCache

@Dao
interface FeedCache {
    @Query("SELECT * FROM FEEDCACHE")
    fun getAll(): List<FeedItemCache> // TODO: Change to be async

    @Insert
    fun insert(itemCache: FeedItemCache)

    @Query("DELETE FROM FEEDCACHE")
    fun invalidate()
}