package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.api.FeedService
import io.fgonzaleva.musicforbooks.data.cache.interfaces.CacheStrategy
import io.fgonzaleva.musicforbooks.data.cache.interfaces.FeedCache
import io.fgonzaleva.musicforbooks.data.cache.model.FeedItemCache
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.FeedRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FeedRepository : FeedRepository, KoinComponent {

    private val cache: FeedCache by inject()
    private val cacheStrategy: CacheStrategy by inject()
    private val feedService: FeedService by inject()

    private fun getCachedFeed(): Single<List<BookItem>> {
        return cache
            .getAll()
            .subscribeOn(Schedulers.io())
            .map { list ->
                list.map { BookItem.fromFeedItemCache(it) }
            }
    }

    private fun requestFeed(): Single<List<BookItem>> {
        return feedService
            .getAll()
            .subscribeOn(Schedulers.io())
            .doOnSuccess {list ->
                cache
                    .invalidate(
                        list.map { FeedItemCache.fromResponse(it) }
                    )
                    .blockingAwait()
            }
            .doOnSuccess { list ->
                cache
                    .insert(
                        list.map { FeedItemCache.fromResponse(it) }
                    )
                    .blockingAwait()
            }
            .map {list ->
                list.map { BookItem.fromFeedItemResponse(it) }
            }
    }

    override fun getFeed(): Single<List<BookItem>> {
        return getCachedFeed()
            .flatMap { list ->
                val cachedItems = list.map { FeedItemCache.fromItem(it) }

                if (cacheStrategy.isCacheValid(cachedItems)) {
                    Single.just(list)
                } else {
                    requestFeed()
                }
            }
    }

}