package io.fgonzaleva.musicforbooks.data.repositories.model

import io.fgonzaleva.musicforbooks.data.cache.model.SpotifyTokenCacheItem
import org.joda.time.Instant

data class SpotifyToken(
    val token: String,
    val type: String,
    val expiresIn: Instant
) {

    companion object {
        fun fromCacheItem(item: SpotifyTokenCacheItem): SpotifyToken {
            return SpotifyToken(
                token = item.token,
                type = "",
                expiresIn = item.expirationTime
            )
        }
    }

}