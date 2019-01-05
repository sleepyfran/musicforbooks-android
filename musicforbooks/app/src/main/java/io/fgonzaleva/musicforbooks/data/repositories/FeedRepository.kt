package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.api.interfaces.MusicForBooksService
import io.fgonzaleva.musicforbooks.data.cache.model.FeedCacheItem
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.CacheRepository
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.FeedRepository
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.util.concurrent.TimeUnit

class FeedRepository : FeedRepository, KoinComponent {

    private val cacheRepository: CacheRepository by inject()
    private val musicForBooksService: MusicForBooksService by inject()

    private fun getCachedFeed(): Maybe<List<BookItem>> {
        return cacheRepository
            .getCachedFeedItems()
            .subscribeOn(Schedulers.io())
            .map { list ->
                list.map { BookItem.fromFeedItemCache(it) }
            }
    }

    private fun requestFeed(): Single<List<BookItem>> {
        return musicForBooksService
            .getFeed()
            .subscribeOn(Schedulers.io())
            .doOnSuccess {
                cacheRepository
                    .invalidateFeedItems()
                    .blockingAwait(5, TimeUnit.SECONDS)
            }
            .doOnSuccess { list ->
                cacheRepository
                    .cacheFeedItems(
                        list.map { FeedCacheItem.fromResponse(it) }
                    )
                    .blockingAwait(5, TimeUnit.SECONDS)
            }
            .map { list ->
                list.map { BookItem.fromFeedItemResponse(it) }
            }
    }

    override fun getFeed(): Single<List<BookItem>> {
        return getCachedFeed()
            .switchIfEmpty(requestFeed())
    }

}