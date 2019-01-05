package io.fgonzaleva.musicforbooks.data.repositories.interfaces

import io.fgonzaleva.musicforbooks.data.cache.model.CacheInvalidationTimeItem
import io.fgonzaleva.musicforbooks.data.cache.model.FeedCacheItem
import io.reactivex.Completable
import io.reactivex.Maybe

interface CacheRepository {
    fun getCacheInvalidationTimeOf(type: CacheInvalidationTimeItem.CacheType): Maybe<CacheInvalidationTimeItem>

    fun cacheFeedItems(feedItems: List<FeedCacheItem>): Completable
    fun getCachedFeedItems(): Maybe<List<FeedCacheItem>>
    fun invalidateFeedItems(): Completable
}