package io.fgonzaleva.musicforbooks.data.repositories.interfaces

import io.fgonzaleva.musicforbooks.data.repositories.model.Book
import io.reactivex.Single

interface BookRepository {
    fun getBook(bookId: Int): Single<Book>
}