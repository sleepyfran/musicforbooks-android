package io.fgonzaleva.musicforbooks.ui

import android.app.Application
import androidx.room.Room
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import io.fgonzaleva.musicforbooks.BuildConfig
import io.fgonzaleva.musicforbooks.data.api.ApiProviders
import io.fgonzaleva.musicforbooks.data.api.CredentialsProvider
import io.fgonzaleva.musicforbooks.data.api.HeaderProvider
import io.fgonzaleva.musicforbooks.data.cache.AppDatabase
import io.fgonzaleva.musicforbooks.data.cache.CacheProviders
import io.fgonzaleva.musicforbooks.data.repositories.RepositoryProviders
import io.fgonzaleva.musicforbooks.ui.book.BookViewModel
import io.fgonzaleva.musicforbooks.ui.components.BookListAdapter
import io.fgonzaleva.musicforbooks.ui.dashboard.DashboardViewModel
import io.fgonzaleva.musicforbooks.ui.search.SearchViewModel
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val database = initializeDatabase()

        // Initialize the Retrofit services.
        val musicForBooksApi = initializeMusicForBooksService()
        val goodReadsApi = initializeGoodReadsService()
        val spotifyAuthApi = initializeSpotifyAuthService()
        val spotifyApi = initializeSpotifyService()

        // Initialize dependency injection.
        val databaseModule = module {
            single { database }
        }

        val apiModule = module {
            single(name = "MusicForBooks") { musicForBooksApi }
            single(name = "GoodReads") { goodReadsApi }
            single(name = "SpotifyAuth") { spotifyAuthApi }
            single(name = "Spotify") { spotifyApi }
            single { HeaderProvider() }
            single {
                CredentialsProvider(
                    BuildConfig.GOODREADS_API_KEY,
                    BuildConfig.SPOTIFY_API_KEY,
                    BuildConfig.SPOTIFY_API_SECRET
                )
            }
        }

        val viewModelsModule = module {
            viewModel { DashboardViewModel(get()) }
            viewModel { SearchViewModel(get()) }
            viewModel { BookViewModel(get()) }
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
            viewModelsModule,
            adaptersModule
        ))
    }

    private fun initializeDatabase(): AppDatabase {
        return Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "musicforbooks"
        ).build()
    }

    private fun initializeMusicForBooksService(): Retrofit {
        val musicForBooksBaseUrl = BuildConfig.BASE_API_URL
        return Retrofit.Builder()
            .baseUrl(musicForBooksBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun initializeGoodReadsService(): Retrofit {
        val goodReadsBaseUrl = BuildConfig.BASE_GOODREADS_URL
        val tikXml = TikXml
            .Builder()
            .exceptionOnUnreadXml(false)
            .build()

        return Retrofit
            .Builder()
            .baseUrl(goodReadsBaseUrl)
            .addConverterFactory(TikXmlConverterFactory.create(tikXml))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun initializeSpotifyAuthService(): Retrofit {
        val spotifyBaseUrl = BuildConfig.BASE_SPOTIFY_AUTH_URL

        return Retrofit
            .Builder()
            .baseUrl(spotifyBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun initializeSpotifyService(): Retrofit {
        val spotifyBaseUrl = BuildConfig.BASE_SPOTIFY_URL

        return Retrofit
            .Builder()
            .baseUrl(spotifyBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}