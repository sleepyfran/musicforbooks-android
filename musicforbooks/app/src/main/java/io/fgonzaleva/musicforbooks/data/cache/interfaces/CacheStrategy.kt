package io.fgonzaleva.musicforbooks.data.cache.interfaces

interface CacheStrategy {
    fun isCacheValid(cachedItems: List<Any>): Boolean
}