package io.fgonzaleva.musicforbooks.data.cache

import io.fgonzaleva.musicforbooks.data.cache.interfaces.CacheStrategy
import io.fgonzaleva.musicforbooks.data.cache.model.CacheInvalidationTimeItem
import org.joda.time.Instant

class CacheStrategy : CacheStrategy {

    override fun isCacheValid(cacheInvalidationTime: CacheInvalidationTimeItem): Boolean {
        return cacheInvalidationTime.expirationTime > Instant()
    }

}