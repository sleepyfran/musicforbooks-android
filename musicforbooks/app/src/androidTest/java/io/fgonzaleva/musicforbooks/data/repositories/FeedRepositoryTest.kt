package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.BaseTest
import io.fgonzaleva.musicforbooks.data.api.FeedService
import io.fgonzaleva.musicforbooks.data.api.model.FeedItemResponse
import io.fgonzaleva.musicforbooks.data.cache.interfaces.CacheStrategy
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.FeedRepository
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.koin.test.declare
import org.koin.test.declareMock
import org.mockito.ArgumentMatchers.anyList
import org.mockito.Mockito

class FeedRepositoryTest : BaseTest(), KoinTest {

    private val feedRepository: FeedRepository by inject()
    private lateinit var feedService: FeedService
    private lateinit var cacheStrategy: CacheStrategy

    private val feedItemResponse = FeedItemResponse(
        authorName = "Test author name",
        bookTitle = "Test book title",
        goodReadsId = 1,
        coverUrl = "Test URL cover"
    )

    @Before
    fun setup() {
        val mockDatabase = buildMockDatabase()

        declare { buildSingleModule(mockDatabase) }
        cacheStrategy = declareMock()
        feedService = declareMock()

        Mockito
            .`when`(feedService.getAll())
            .thenReturn(Single.just(
                listOf(
                    feedItemResponse
                )
            ))
    }

    @Test
    fun getFeed_should_fetch_data_from_the_API_if_no_cache_is_present() {
        Mockito
            .`when`(cacheStrategy.isCacheValid(anyList()))
            .thenReturn(false)

        feedRepository
            .getFeed()
            .subscribe { list ->
                list.forEach { item ->
                    assertThat(item).isEqualTo(feedItemResponse)
                }
            }
    }

    @Test
    fun getFeed_should_return_cached_items_when_cache_is_present() {
        Mockito
            .`when`(cacheStrategy.isCacheValid(anyList()))
            .thenReturn(true)

        feedRepository
            .getFeed()
            .subscribe { list ->
                list.forEach { item ->
                    assertThat(item).isEqualToComparingFieldByField(feedItemResponse)
                }
            }
    }

}