package io.fgonzaleva.musicforbooks.ui

import android.app.Application
import androidx.room.Room
import io.fgonzaleva.musicforbooks.BuildConfig
import io.fgonzaleva.musicforbooks.data.api.ApiProviders
import io.fgonzaleva.musicforbooks.data.cache.AppDatabase
import io.fgonzaleva.musicforbooks.data.cache.CacheProviders
import io.fgonzaleva.musicforbooks.data.repositories.RepositoryProviders
import io.fgonzaleva.musicforbooks.ui.dashboard.DashboardPresenter
import org.koin.android.ext.android.startKoin
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    /**
     * Unique instance of the database.
     */
    lateinit var database: AppDatabase

    /**
     * Unique instance of the Retrofit adapter.
     */
    lateinit var retrofit: Retrofit

    override fun onCreate() {
        super.onCreate()

        // Initialize the cache database.
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "musicforbooks"
        ).build()

        // Initialize the Retrofit service.
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Initialize dependency injection.
        val databaseModule = module {
            single { database }
        }

        val retrofitModule = module {
            single { retrofit }
        }

        val presentersModule = module {
            factory { DashboardPresenter() }
        }

        startKoin(applicationContext, listOf(
            databaseModule,
            retrofitModule,
            CacheProviders().module,
            ApiProviders().module,
            RepositoryProviders().module,
            presentersModule
        ))
    }
}