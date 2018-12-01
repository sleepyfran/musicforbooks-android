package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.api.CredentialsProvider
import io.fgonzaleva.musicforbooks.data.api.interfaces.GoodReadsService
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.BookRepository
import io.fgonzaleva.musicforbooks.data.repositories.model.Book
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class BookRepository : BookRepository, KoinComponent {

    private val credentialsProvider: CredentialsProvider by inject()
    private val goodReadsService: GoodReadsService by inject()

    override fun getBook(bookId: Int): Single<Book> {
        return goodReadsService
            .getBook(bookId, credentialsProvider.goodReadsDeveloperKey)
            .subscribeOn(Schedulers.io())
            .map {
                Book.fromBookResponse(it)
            }
    }

}