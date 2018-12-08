package io.fgonzaleva.musicforbooks.data.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.fgonzaleva.musicforbooks.data.api.model.spotify.TokenResponse
import org.joda.time.Instant
import java.util.*

@Entity(tableName = "spotifytokencache")
data class SpotifyTokenItemCache(
    @PrimaryKey var uid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "token") var token: String,
    @ColumnInfo(name = "expiration_time") var expirationTime: Instant
) {

    companion object {
        fun fromResponse(response: TokenResponse): SpotifyTokenItemCache {
            return SpotifyTokenItemCache(
                token = "Bearer ${response.accessToken}",
                expirationTime = Instant().plus(response.expiresIn)
            )
        }
    }

}