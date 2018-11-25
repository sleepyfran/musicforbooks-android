package io.fgonzaleva.musicforbooks

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import io.fgonzaleva.musicforbooks.data.cache.AppDatabase
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

open class BaseTest {

    fun buildMockDatabase(): AppDatabase {
        return Room
            .inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                AppDatabase::class.java
            )
            .allowMainThreadQueries()
            .build()
    }

    inline fun <reified T : Any> buildSingleModule(dependency: T): Module {
        return module {
            single { dependency }
        }
    }

    inline fun <reified T : Any> buildFactoryModule(dependency: T): Module {
        return module {
            factory { dependency }
        }
    }

}