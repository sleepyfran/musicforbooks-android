package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.api.FeedService
import io.fgonzaleva.musicforbooks.data.cache.FeedCache
import io.fgonzaleva.musicforbooks.data.cache.model.FeedItemCache
import io.fgonzaleva.musicforbooks.data.repositories.model.FeedItem
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.FeedRepository
import io.reactivex.Single
import org.joda.time.DateTime
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class FeedRepository : FeedRepository, KoinComponent {

    private val cache: FeedCache by inject()
    private val feedService: FeedService by inject()
    private var nextInvalidationTime = DateTime().plusDays(10)

    override fun getFeed(): Single<List<FeedItem>> {
        val cachedItems = cache.getAll()
        val rightNow = DateTime()

        val isCacheValid = !cachedItems.isEmpty() && nextInvalidationTime > rightNow
        if (isCacheValid) {
            return Single
                .just(
                    cachedItems.map {
                        FeedItem(it.authorName, it.bookTitle, it.goodReadsId, it.coverUrl)
                    }
                )
        }

        cache.invalidate()

        return feedService
            .getAll()
            .map {
                cache.insert(
                    FeedItemCache(
                        authorName = it.authorName,
                        bookTitle = it.bookTitle,
                        goodReadsId = it.goodReadsId,
                        coverUrl = it.coverUrl
                    )
                )
                it
            }
            .map {
                FeedItem(
                    it.authorName,
                    it.bookTitle,
                    it.goodReadsId,
                    it.coverUrl
                )
            }
            .toList()
    }

}