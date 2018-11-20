package io.fgonzaleva.musicforbooks.data.cache

import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class CacheProviders : KoinComponent {

    private val database: AppDatabase by inject()

    val module = module {
        single { database.feedCache() }
    }

}