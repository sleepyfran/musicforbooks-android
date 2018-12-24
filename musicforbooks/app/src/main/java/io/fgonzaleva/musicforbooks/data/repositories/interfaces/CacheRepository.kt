package io.fgonzaleva.musicforbooks.data.repositories.interfaces

import io.fgonzaleva.musicforbooks.data.cache.model.CacheInvalidationTimeItem
import io.fgonzaleva.musicforbooks.data.cache.model.FeedItemCache
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single

interface CacheRepository {
    fun getCacheInvalidationTimeOf(type: CacheInvalidationTimeItem.CacheType): Maybe<CacheInvalidationTimeItem>

    fun cacheFeedItems(feedItems: List<FeedItemCache>): Completable
    fun getCachedFeedItems(): Maybe<List<FeedItemCache>>
    fun invalidateFeedItems(): Completable
}