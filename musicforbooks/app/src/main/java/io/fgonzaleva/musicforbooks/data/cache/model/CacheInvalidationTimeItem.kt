package io.fgonzaleva.musicforbooks.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.Instant
import java.util.*

@Entity(tableName = "cacheinvalidationtime")
data class CacheInvalidationTimeItem(
    @PrimaryKey var uid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "cache_type") var cacheType: CacheType,
    @ColumnInfo(name = "expiration_time") var expirationTime: Instant
) {

    enum class CacheType {
        FEED
    }

}