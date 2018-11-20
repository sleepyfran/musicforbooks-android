package io.fgonzaleva.musicforbooks.data.api

import io.fgonzaleva.musicforbooks.data.api.model.FeedItemResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface FeedService {
    @GET("dashboard")
    fun getAll(): Observable<FeedItemResponse>
}