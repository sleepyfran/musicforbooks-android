package io.fgonzaleva.musicforbooks.data.api.interfaces

import io.fgonzaleva.musicforbooks.data.api.model.musicforbooks.BookResponse
import io.fgonzaleva.musicforbooks.data.api.model.musicforbooks.FeedItemResponse
import io.reactivex.Single
import retrofit2.http.*

interface MusicForBooksService {

    @GET("dashboard/")
    fun getFeed(): Single<List<FeedItemResponse>>

    @GET("books/{id}")
    fun getBookSongs(@Path("id") bookId: Int): Single<BookResponse>

    @GET("songs/{id}")
    fun getBooksForSong(@Path("id") songId: String): Single<List<BookResponse>>

    @GET("songs/")
    fun getBooksForSongs(@Query("ids") ids: String): Single<List<BookResponse>>

    @POST("books/{id}")
    fun updateBook(@Path("id") bookId: Int, @Body songs: List<String>): Single<BookResponse>

}