package io.fgonzaleva.musicforbooks.data.api

import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Retrofit

class ApiProviders : KoinComponent {

    private val retrofit: Retrofit by inject()

    val module = module {
        single<FeedService> { retrofit.create(FeedService::class.java) }
    }

}