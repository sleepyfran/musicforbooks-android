package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.api.CredentialsProvider
import io.fgonzaleva.musicforbooks.data.api.interfaces.GoodReadsService
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SearchRepository
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SearchRepository : SearchRepository, KoinComponent {

    private val goodReadsService: GoodReadsService by inject()
    private val credentialsProvider: CredentialsProvider by inject()

    override fun searchBook(query: String): Single<List<BookItem>> {
        return goodReadsService
            .search(query, 1, credentialsProvider.goodReadsDeveloperKey, "title")
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.results.map {
                    BookItem.fromBookResultResponse(it)
                }
            }
    }

}