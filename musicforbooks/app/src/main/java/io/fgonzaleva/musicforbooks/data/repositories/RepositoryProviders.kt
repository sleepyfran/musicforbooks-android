package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.repositories.interfaces.BookRepository
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.CacheRepository
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.FeedRepository
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SearchRepository
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SongRepository
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SpotifyTokenRepository
import org.koin.dsl.module.module

class RepositoryProviders {

    val module = module {
        factory<FeedRepository> { io.fgonzaleva.musicforbooks.data.repositories.FeedRepository() }
        factory<SearchRepository> { io.fgonzaleva.musicforbooks.data.repositories.SearchRepository() }
        factory<BookRepository> { io.fgonzaleva.musicforbooks.data.repositories.BookRepository() }
        factory<SpotifyTokenRepository> { io.fgonzaleva.musicforbooks.data.repositories.SpotifyTokenRepository() }
        factory<SongRepository> { io.fgonzaleva.musicforbooks.data.repositories.SongRepository(get(), get(), get()) }
        factory<CacheRepository> {
            io.fgonzaleva.musicforbooks.data.repositories.CacheRepository()
        }
    }

}