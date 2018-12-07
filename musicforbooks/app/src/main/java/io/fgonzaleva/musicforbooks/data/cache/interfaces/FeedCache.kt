package io.fgonzaleva.musicforbooks.data.cache.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.fgonzaleva.musicforbooks.data.cache.model.FeedItemCache
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface FeedCache {

    @Query("SELECT * FROM FEEDCACHE")
    fun getAll(): Single<List<FeedItemCache>>

    @Insert
    fun insert(items: List<FeedItemCache>): Completable

    @Query("DELETE FROM FEEDCACHE")
    fun invalidate()

}