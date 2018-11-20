package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.repositories.interfaces.FeedRepository
import org.koin.dsl.module.module

class RepositoryProviders {

    val module = module {
        factory<FeedRepository> { io.fgonzaleva.musicforbooks.data.repositories.FeedRepository() }
    }

}