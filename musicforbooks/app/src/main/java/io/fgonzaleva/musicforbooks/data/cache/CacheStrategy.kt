package io.fgonzaleva.musicforbooks.data.cache

import io.fgonzaleva.musicforbooks.data.cache.interfaces.CacheStrategy
import org.joda.time.DateTime

class CacheStrategy : CacheStrategy {

    var invalidationTime: DateTime = DateTime().plusDays(10)

    override fun isCacheValid(cachedItems: List<Any>): Boolean {
        return !cachedItems.isEmpty() && invalidationTime > DateTime()
    }

}