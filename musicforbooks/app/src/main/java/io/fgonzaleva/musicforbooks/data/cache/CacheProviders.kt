package io.fgonzaleva.musicforbooks.data.cache

import io.fgonzaleva.musicforbooks.data.cache.interfaces.CacheStrategy
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent

class CacheProviders : KoinComponent {

    val module = module {
        single { get<AppDatabase>().feedCache() }
        single<CacheStrategy> { io.fgonzaleva.musicforbooks.data.cache.CacheStrategy() }
    }

}