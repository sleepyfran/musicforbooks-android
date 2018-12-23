package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.api.CredentialsProvider
import io.fgonzaleva.musicforbooks.data.api.interfaces.GoodReadsService
import io.fgonzaleva.musicforbooks.data.api.interfaces.MusicForBooksService
import io.fgonzaleva.musicforbooks.data.api.interfaces.SpotifyService
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.BookRepository
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SearchRepository
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SongRepository
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SearchRepository : SearchRepository, KoinComponent {

    private val musicForBooksService: MusicForBooksService by inject()
    private val goodReadsService: GoodReadsService by inject()
    private val songRepository: SongRepository by inject()
    private val bookRepository: BookRepository by inject()
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

    override fun searchSong(query: String): Single<List<BookItem>> {
        return songRepository
            .searchSongs(query)
            .flatMap { songs ->
                Single.just(songs.map { it.id })
            }
            .flatMap { ids ->
                val reducedIds = ids.reduce { acc, id -> "$acc,$id" }
                Single.just(reducedIds)
            }
            .flatMap { ids ->
                musicForBooksService
                    .getBooksForSongs(ids)
            }
            .flatMap { responses ->
                Observable
                    .fromIterable(responses)
                    .flatMapSingle {
                        goodReadsService
                            .getBook(it.goodReadsId, credentialsProvider.goodReadsDeveloperKey)
                    }
                    .toList()
            }
            .map { books ->
                books.map {
                    BookItem.fromBookResponse(it)
                }
            }
    }

}