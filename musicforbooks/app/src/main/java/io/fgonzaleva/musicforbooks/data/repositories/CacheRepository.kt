package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.cache.interfaces.CacheInvalidationTime
import io.fgonzaleva.musicforbooks.data.cache.interfaces.CacheStrategy
import io.fgonzaleva.musicforbooks.data.cache.interfaces.FeedCache
import io.fgonzaleva.musicforbooks.data.cache.model.CacheInvalidationTimeItem
import io.fgonzaleva.musicforbooks.data.cache.model.FeedItemCache
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.CacheRepository
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.joda.time.DateTime
import org.joda.time.Instant
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class CacheRepository : CacheRepository, KoinComponent {

    private val cacheStrategy: CacheStrategy by inject()
    private val cacheInvalidationTime: CacheInvalidationTime by inject()
    private val feedCache: FeedCache by inject()

    override fun getCacheInvalidationTimeOf(type: CacheInvalidationTimeItem.CacheType):
            Maybe<CacheInvalidationTimeItem> {
        return cacheInvalidationTime.getByType(type)
    }

    override fun cacheFeedItems(feedItems: List<FeedItemCache>): Completable {
        val tenDays = DateTime().plusDays(10).toInstant()
        val invalidationTime = CacheInvalidationTimeItem(
            cacheType = CacheInvalidationTimeItem.CacheType.FEED,
            expirationTime = tenDays
        )

        return feedCache
            .insert(feedItems)
            .andThen(cacheInvalidationTime.insert(invalidationTime))
    }

    override fun getCachedFeedItems(): Maybe<List<FeedItemCache>> {
        return feedCache
            .getAll()
            .flatMapMaybe { items ->
                getCacheInvalidationTimeOf(CacheInvalidationTimeItem.CacheType.FEED)
                    .switchIfEmpty(
                        Maybe.just(
                            CacheInvalidationTimeItem(
                                cacheType = CacheInvalidationTimeItem.CacheType.FEED,
                                expirationTime = Instant()
                            )
                        )
                    )
                    .map { Pair(it, items) }
            }
            .flatMap { (invalidationTime, items) ->
                if (cacheStrategy.isCacheValid(invalidationTime)) {
                    Maybe.just(items)
                } else {
                    Maybe.empty()
                }
            }
    }

    override fun invalidateFeedItems(): Completable {
        return Completable.fromCallable(feedCache::invalidate)
    }

}