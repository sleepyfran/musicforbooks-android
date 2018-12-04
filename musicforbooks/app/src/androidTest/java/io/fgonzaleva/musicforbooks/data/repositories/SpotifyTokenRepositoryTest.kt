package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.BaseTest
import io.fgonzaleva.musicforbooks.data.api.interfaces.SpotifyAuthService
import io.fgonzaleva.musicforbooks.data.api.model.spotify.TokenResponse
import io.fgonzaleva.musicforbooks.data.cache.interfaces.SpotifyTokenCache
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SpotifyTokenRepository
import io.reactivex.Single
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.koin.standalone.get
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.koin.test.declareMock
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito

class SpotifyTokenRepositoryTest : BaseTest(), KoinTest {

    private val spotifyTokenRepository: SpotifyTokenRepository by inject()
    private lateinit var spotifyAuthService: SpotifyAuthService

    private val tokenResponse = TokenResponse(
        "Test Access Token",
        "Test Token Type",
        3600
    )

    @Before
    fun setup() {
        buildMockDatabase()
        spotifyAuthService = declareMock()
    }

    @Test
    fun getTokenOrGenerate_should_fetch_token_from_the_API_and_refresh_cache_if_no_cache_is_present() {
        Mockito
            .`when`(spotifyAuthService.getToken(anyString(), any()))
            .thenReturn(
                Single.just(tokenResponse)
            )

        val database = get<SpotifyTokenCache>()

        spotifyTokenRepository
            .getTokenOrGenerate()
            .subscribe { token ->
                assertThat(token)
                    .isEqualToComparingFieldByField(tokenResponse)

                database
                    .getAll()
                    .subscribe { items ->
                        // Check that the database contains something.
                        assertThat(items)
                            .isNotEmpty()

                        // Check that this something is the tokenResponse.
                        val firstItem = items.first()
                        assertThat(firstItem)
                            .isEqualToComparingFieldByField(tokenResponse)
                    }
            }
    }

    @Test
    fun getTokenOrGenerate_should_fetch_token_from_the_API_and_refresh_cache_if_cache_is_not_valid() {

    }

    @Test
    fun getTokenOrGenerate_should_return_cache_if_it_is_valid() {

    }

}