package io.fgonzaleva.musicforbooks

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import io.fgonzaleva.musicforbooks.data.cache.AppDatabase
import org.koin.test.KoinTest
import org.koin.test.declare
import org.mockito.Mockito

open class BaseTest : KoinTest {

    protected fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

    fun buildMockDatabase() {
        val mockDatabase = Room
            .inMemoryDatabaseBuilder(
                InstrumentationRegistry.getInstrumentation().context,
                AppDatabase::class.java
            )
            .build()

        declare { single(override = true) { mockDatabase } }
    }

}