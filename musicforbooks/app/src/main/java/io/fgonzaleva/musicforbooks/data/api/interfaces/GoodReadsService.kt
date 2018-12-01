package io.fgonzaleva.musicforbooks.data.api.interfaces

import io.fgonzaleva.musicforbooks.data.api.model.goodreads.BookResponse
import io.fgonzaleva.musicforbooks.data.api.model.goodreads.SearchResultResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoodReadsService {
    @GET("/search/index.xml")
    fun search(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("key") key: String,
        @Query("search[field]") field: String
    ): Single<SearchResultResponse>

    @GET("book/show/{id}")
    fun getBook(
        @Path("id") bookId: Int,
        @Query("key") key: String
    ): Single<BookResponse>
}