package io.fgonzaleva.musicforbooks.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.fgonzaleva.musicforbooks.data.cache.interfaces.FeedCache
import io.fgonzaleva.musicforbooks.data.cache.interfaces.SpotifyTokenCache
import io.fgonzaleva.musicforbooks.data.cache.model.FeedItemCache
import io.fgonzaleva.musicforbooks.data.cache.model.SpotifyTokenItemCache

@Database(entities = [FeedItemCache::class, SpotifyTokenItemCache::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun feedCache(): FeedCache
    abstract fun spotifyTokenCache(): SpotifyTokenCache
}