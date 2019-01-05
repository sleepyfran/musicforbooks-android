package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.api.HeaderProvider
import io.fgonzaleva.musicforbooks.data.api.interfaces.SpotifyAuthService
import io.fgonzaleva.musicforbooks.data.cache.interfaces.SpotifyTokenCache
import io.fgonzaleva.musicforbooks.data.cache.model.SpotifyTokenCacheItem
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SpotifyTokenRepository
import io.fgonzaleva.musicforbooks.data.repositories.model.SpotifyToken
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.joda.time.Instant
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.util.concurrent.TimeUnit

class SpotifyTokenRepository : SpotifyTokenRepository, KoinComponent {

    private val spotifyTokenCache: SpotifyTokenCache by inject()
    private val spotifyAuthService: SpotifyAuthService by inject()
    private val headerProvider: HeaderProvider by inject()

    override fun getTokenOrGenerate(): Single<SpotifyToken> {
        return spotifyTokenCache
            .getLatest()
            .subscribeOn(Schedulers.io())
            .filter { it.expirationTime > Instant() }
            .switchIfEmpty(Single.defer {
                spotifyAuthService
                    .getToken(headerProvider.generateSpotifyAuthHeader())
                    .map { response ->
                        SpotifyTokenCacheItem.fromResponse(response)
                    }
                    .doOnSuccess {
                        Completable
                            .fromAction(spotifyTokenCache::invalidate)
                            .blockingAwait(5, TimeUnit.SECONDS)
                    }
                    .doOnSuccess {
                        spotifyTokenCache
                            .insert(it)
                            .blockingAwait(5, TimeUnit.SECONDS)
                    }
            })
            .map {
                SpotifyToken.fromCacheItem(it)
            }
    }

}