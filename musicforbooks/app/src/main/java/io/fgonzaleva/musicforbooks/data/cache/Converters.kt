package io.fgonzaleva.musicforbooks.data.cache

import androidx.room.TypeConverter
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

}