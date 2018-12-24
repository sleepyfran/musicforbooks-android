package io.fgonzaleva.musicforbooks.data.cache.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.fgonzaleva.musicforbooks.data.cache.model.CacheInvalidationTimeItem
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface CacheInvalidationTime {

    @Query("SELECT * FROM CACHEINVALIDATIONTIME WHERE CACHE_TYPE = :cacheType")
    fun getByType(cacheType: CacheInvalidationTimeItem.CacheType): Maybe<CacheInvalidationTimeItem>

    @Insert
    fun insert(cacheInvalidationTime: CacheInvalidationTimeItem): Completable

    @Delete
    fun invalidate(cacheInvalidationTime: CacheInvalidationTimeItem): Completable

}