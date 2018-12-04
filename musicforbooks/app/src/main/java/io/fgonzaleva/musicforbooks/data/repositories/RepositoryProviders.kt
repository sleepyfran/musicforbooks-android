package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.repositories.interfaces.BookRepository
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.FeedRepository
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SearchRepository
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SpotifyTokenRepository
import org.koin.dsl.module.module

class RepositoryProviders {

    val module = module {
        factory<FeedRepository> { io.fgonzaleva.musicforbooks.data.repositories.FeedRepository() }
        factory<SearchRepository> { io.fgonzaleva.musicforbooks.data.repositories.SearchRepository() }
        factory<BookRepository> { io.fgonzaleva.musicforbooks.data.repositories.BookRepository() }
        factory<SpotifyTokenRepository> { io.fgonzaleva.musicforbooks.data.repositories.SpotifyTokenRepository() }
    }

}