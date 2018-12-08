package io.fgonzaleva.musicforbooks.data.api

import io.fgonzaleva.musicforbooks.data.api.interfaces.MusicForBooksService
import io.fgonzaleva.musicforbooks.data.api.interfaces.GoodReadsService
import io.fgonzaleva.musicforbooks.data.api.interfaces.SpotifyAuthService
import io.fgonzaleva.musicforbooks.data.api.interfaces.SpotifyService
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Retrofit

class ApiProviders : KoinComponent {

    private val musicForBooksApi: Retrofit by inject("MusicForBooks")
    private val goodReadsApi: Retrofit by inject("GoodReads")
    private val spotifyAuthApi: Retrofit by inject("SpotifyAuth")
    private val spotifyApi: Retrofit by inject("Spotify")

    val module = module {
        single<MusicForBooksService> { musicForBooksApi.create(MusicForBooksService::class.java) }
        single<GoodReadsService> { goodReadsApi.create(GoodReadsService::class.java) }
        single<SpotifyAuthService> { spotifyAuthApi.create(SpotifyAuthService::class.java) }
        single<SpotifyService> { spotifyApi.create(SpotifyService::class.java) }
    }

}