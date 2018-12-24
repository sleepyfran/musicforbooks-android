package io.fgonzaleva.musicforbooks.data.cache.interfaces

import io.fgonzaleva.musicforbooks.data.cache.model.CacheInvalidationTimeItem

interface CacheStrategy {
    fun isCacheValid(cacheInvalidationTime: CacheInvalidationTimeItem): Boolean
}