package io.fgonzaleva.musicforbooks.data.api

import io.fgonzaleva.musicforbooks.data.api.interfaces.FeedService
import io.fgonzaleva.musicforbooks.data.api.interfaces.GoodReadsService
import org.koin.dsl.module.module
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import retrofit2.Retrofit

class ApiProviders : KoinComponent {

    private val musicForBooksApi: Retrofit by inject("MusicForBooks")
    private val goodReadsApi: Retrofit by inject("GoodReads")

    val module = module {
        single<FeedService> { musicForBooksApi.create(FeedService::class.java) }
        single<GoodReadsService> { goodReadsApi.create(GoodReadsService::class.java) }
    }

}