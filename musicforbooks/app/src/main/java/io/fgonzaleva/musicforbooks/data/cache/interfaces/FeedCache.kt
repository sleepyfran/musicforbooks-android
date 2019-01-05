package io.fgonzaleva.musicforbooks.data.cache.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.fgonzaleva.musicforbooks.data.cache.model.FeedCacheItem
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FeedCache {

    @Query("SELECT * FROM FEEDCACHE")
    fun getAll(): Single<List<FeedCacheItem>>

    @Insert
    fun insert(items: List<FeedCacheItem>): Completable

    @Query("DELETE FROM FEEDCACHE")
    fun invalidate()

}