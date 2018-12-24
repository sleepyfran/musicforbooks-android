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
import io.fgonzaleva.musicforbooks.ui.addsong.AddSongViewModel
import io.fgonzaleva.musicforbooks.ui.book.BookViewModel
import io.fgonzaleva.musicforbooks.ui.components.BookListAdapter
import io.fgonzaleva.musicforbooks.ui.components.SongListAdapter
import io.fgonzaleva.musicforbooks.ui.dashboard.DashboardViewModel
import io.fgonzaleva.musicforbooks.ui.search.SearchViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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

        // Initialize the OkHttpClient.
        val okHttpClient = if (BuildConfig.DEBUG) {
            val httpInterceptor = HttpLoggingInterceptor()
            httpInterceptor.level = HttpLoggingInterceptor.Level.BODY

            OkHttpClient
                .Builder()
                .addInterceptor(httpInterceptor)
                .build()
        } else {
            OkHttpClient()
        }

        // Initialize the Retrofit services.
        val musicForBooksApi = initializeMusicForBooksService(okHttpClient)
        val goodReadsApi = initializeGoodReadsService(okHttpClient)
        val spotifyAuthApi = initializeSpotifyAuthService(okHttpClient)
        val spotifyApi = initializeSpotifyService(okHttpClient)

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
            viewModel { BookViewModel(get(), get()) }
            viewModel { AddSongViewModel(get()) }
        }

        val adaptersModule = module {
            factory { BookListAdapter() }
            factory { SongListAdapter() }
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
        return Room
            .databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "musicforbooks"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun initializeMusicForBooksService(client: OkHttpClient): Retrofit {
        val musicForBooksBaseUrl = BuildConfig.BASE_API_URL
        return Retrofit
            .Builder()
            .baseUrl(musicForBooksBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun initializeGoodReadsService(client: OkHttpClient): Retrofit {
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

    private fun initializeSpotifyAuthService(client: OkHttpClient): Retrofit {
        val spotifyBaseUrl = BuildConfig.BASE_SPOTIFY_AUTH_URL

        return Retrofit
            .Builder()
            .client(client)
            .baseUrl(spotifyBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun initializeSpotifyService(client: OkHttpClient): Retrofit {
        val spotifyBaseUrl = BuildConfig.BASE_SPOTIFY_URL

        return Retrofit
            .Builder()
            .client(client)
            .baseUrl(spotifyBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

}