package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.api.FeedService
import io.fgonzaleva.musicforbooks.data.cache.interfaces.CacheStrategy
import io.fgonzaleva.musicforbooks.data.cache.interfaces.FeedCache
import io.fgonzaleva.musicforbooks.data.cache.model.FeedItemCache
import io.fgonzaleva.musicforbooks.data.repositories.model.FeedItem
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.FeedRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FeedRepository : FeedRepository, KoinComponent {

    private val cache: FeedCache by inject()
    private val cacheStrategy: CacheStrategy by inject()
    private val feedService: FeedService by inject()

    private fun getCachedFeed(): Single<List<FeedItem>> {
        return cache
            .getAll()
            .subscribeOn(Schedulers.io())
            .map { list ->
                list.map {
                    FeedItem(
                        it.authorName,
                        it.bookTitle,
                        it.goodReadsId,
                        it.coverUrl
                    )
                }
            }
    }

    private fun requestFeed(): Single<List<FeedItem>> {
        return feedService
            .getAll()
            .subscribeOn(Schedulers.io())
            .doOnSuccess {list ->
                cache
                    .invalidate(
                        list.map {
                            FeedItemCache(
                                authorName = it.authorName,
                                bookTitle = it.bookTitle,
                                goodReadsId = it.goodReadsId,
                                coverUrl = it.coverUrl
                            )
                        }
                    )
                    .blockingAwait()
            }
            .doOnSuccess { list ->
                cache
                    .insert(
                        list.map {
                            FeedItemCache(
                                authorName = it.authorName,
                                bookTitle = it.bookTitle,
                                goodReadsId = it.goodReadsId,
                                coverUrl = it.coverUrl
                            )
                        }
                    )
                    .blockingAwait()
            }
            .map {list ->
                list.map {
                    FeedItem(
                        it.authorName,
                        it.bookTitle,
                        it.goodReadsId,
                        it.coverUrl
                    )
                }
            }
    }

    override fun getFeed(): Single<List<FeedItem>> {
        return getCachedFeed()
            .flatMap { list ->
                val cachedItems = list.map {
                    FeedItemCache(
                        authorName = it.authorName,
                        bookTitle = it.bookTitle,
                        goodReadsId = it.goodReadsId,
                        coverUrl = it.coverUrl
                    )
                }

                if (cacheStrategy.isCacheValid(cachedItems)) {
                    Single.just(list)
                } else {
                    requestFeed()
                }
            }
    }

}