package io.fgonzaleva.musicforbooks.data.api.interfaces

import io.fgonzaleva.musicforbooks.data.api.model.BookResponse
import io.fgonzaleva.musicforbooks.data.api.model.FeedItemResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MusicForBooksService {

    @GET("dashboard/")
    fun getFeed(): Single<List<FeedItemResponse>>

    @GET("books/{id}")
    fun getBookSongs(@Path("id") bookId: Int): Single<BookResponse>

}