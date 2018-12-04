package io.fgonzaleva.musicforbooks.data.repositories.model

import io.fgonzaleva.musicforbooks.data.cache.model.SpotifyTokenItemCache
import org.joda.time.Instant

data class SpotifyToken(
    val token: String,
    val type: String,
    val expiresIn: Instant
) {

    companion object {
        fun fromCacheItem(item: SpotifyTokenItemCache): SpotifyToken {
            return SpotifyToken(
                token = item.token,
                type = "",
                expiresIn = item.expirationTime
            )
        }
    }

}