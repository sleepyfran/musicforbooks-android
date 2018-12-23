package io.fgonzaleva.musicforbooks.data.repositories.interfaces

import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import io.reactivex.Single

interface SearchRepository {
    fun searchBook(query: String): Single<List<BookItem>>
    fun searchSong(query: String): Single<List<BookItem>>
}