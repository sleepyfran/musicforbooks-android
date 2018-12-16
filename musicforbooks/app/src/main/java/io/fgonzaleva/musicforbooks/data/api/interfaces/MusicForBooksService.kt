package io.fgonzaleva.musicforbooks.data.api.interfaces

import io.fgonzaleva.musicforbooks.data.api.model.musicforbooks.BookResponse
import io.fgonzaleva.musicforbooks.data.api.model.musicforbooks.FeedItemResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MusicForBooksService {

    @GET("dashboard/")
    fun getFeed(): Single<List<FeedItemResponse>>

    @GET("books/{id}")
    fun getBookSongs(@Path("id") bookId: Int): Single<BookResponse>

    @POST("books/{id}")
    fun updateBook(@Path("id") bookId: Int, @Body songs: List<String>): Single<BookResponse>

}