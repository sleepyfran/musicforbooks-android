package io.fgonzaleva.musicforbooks.data.cache

import androidx.room.TypeConverter
import io.fgonzaleva.musicforbooks.data.cache.model.CacheInvalidationTimeItem
import org.joda.time.Instant

class Converters {

    @TypeConverter
    fun fromTimestamp(timestamp: Long?): Instant? {
        return timestamp?.let { Instant(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Instant?): Long? {
        return date?.millis
    }

    @TypeConverter
    fun fromCacheType(cacheType: CacheInvalidationTimeItem.CacheType): String {
        return cacheType.toString()
    }

    @TypeConverter
    fun toCacheType(cacheType: String): CacheInvalidationTimeItem.CacheType {
        return CacheInvalidationTimeItem.CacheType.valueOf(cacheType)
    }

}