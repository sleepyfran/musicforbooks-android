package io.fgonzaleva.musicforbooks.ui

import android.app.Application
import androidx.room.Room
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import io.fgonzaleva.musicforbooks.BuildConfig
import io.fgonzaleva.musicforbooks.data.api.ApiProviders
import io.fgonzaleva.musicforbooks.data.api.CredentialsProvider
import io.fgonzaleva.musicforbooks.data.cache.AppDatabase
import io.fgonzaleva.musicforbooks.data.cache.CacheProviders
import io.fgonzaleva.musicforbooks.data.repositories.RepositoryProviders
import io.fgonzaleva.musicforbooks.ui.components.BookListAdapter
import io.fgonzaleva.musicforbooks.ui.dashboard.DashboardPresenter
import io.fgonzaleva.musicforbooks.ui.search.SearchPresenter
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    /**
     * Unique instance of the database.
     */
    private lateinit var database: AppDatabase

    /**
     * Unique instance of the Retrofit adapters.
     */
    private lateinit var musicForBooksApi: Retrofit
    private lateinit var goodReadsApi: Retrofit

    override fun onCreate() {
        super.onCreate()

        // Initialize the cache database.
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "musicforbooks"
        ).build()

        // Initialize the Retrofit services.
        val musicForBooksBaseUrl = BuildConfig.BASE_API_URL
        musicForBooksApi = Retrofit.Builder()
            .baseUrl(musicForBooksBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        
        val goodReadsBaseUrl = BuildConfig.BASE_GOODREADS_URL
        val tikXml = TikXml
            .Builder()
            .exceptionOnUnreadXml(false)
            .build()
        goodReadsApi = Retrofit
            .Builder()
            .baseUrl(goodReadsBaseUrl)
            .addConverterFactory(TikXmlConverterFactory.create(tikXml))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        // Initialize dependency injection.
        val databaseModule = module {
            single { database }
        }

        val apiModule = module {
            single(name = "MusicForBooks") { musicForBooksApi }
            single(name = "GoodReads") { goodReadsApi }
            single { CredentialsProvider(BuildConfig.GOODREADS_API_KEY) }
        }

        val presentersModule = module {
            factory { DashboardPresenter() }
            factory { SearchPresenter() }
        }

        val adaptersModule = module {
            factory { BookListAdapter() }
        }

        startKoin(applicationContext, listOf(
            databaseModule,
            apiModule,
            CacheProviders().module,
            ApiProviders().module,
            RepositoryProviders().module,
            presentersModule,
            adaptersModule
        ))
    }
}