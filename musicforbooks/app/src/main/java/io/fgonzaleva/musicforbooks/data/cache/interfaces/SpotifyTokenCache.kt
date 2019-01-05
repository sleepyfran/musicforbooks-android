package io.fgonzaleva.musicforbooks.data.cache.interfaces

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.fgonzaleva.musicforbooks.data.cache.model.SpotifyTokenCacheItem
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface SpotifyTokenCache {

    @Query("SELECT * FROM SPOTIFYTOKENCACHE")
    fun getAll(): Single<List<SpotifyTokenCacheItem>>

    @Query("SELECT * FROM SPOTIFYTOKENCACHE ORDER BY expiration_time LIMIT 1")
    fun getLatest(): Maybe<SpotifyTokenCacheItem>

    @Insert
    fun insert(token: SpotifyTokenCacheItem): Completable

    @Query("DELETE FROM SPOTIFYTOKENCACHE")
    fun invalidate()

}