package io.fgonzaleva.musicforbooks.data.api.interfaces

import io.fgonzaleva.musicforbooks.data.api.model.spotify.TrackResponse
import io.fgonzaleva.musicforbooks.data.api.model.spotify.TrackFeaturesResponse
import io.fgonzaleva.musicforbooks.data.api.model.spotify.TrackSearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SpotifyService {

    @GET("tracks")
    fun getTracks(
        @Header("Authorization") authorizationHeader: String,
        @Query("ids") ids: String
    ): Observable<TrackResponse>

    @GET("audio-features")
    fun getTracksFeatures(
        @Header("Authorization") authorizationHeader: String,
        @Query("ids") ids: String
    ): Observable<TrackFeaturesResponse>

    @GET("search")
    fun search(
        @Header("Authorization") authorizationHeader: String,
        @Query("q") query: String,
        @Query("type") type: String = "track",
        @Query("limit") limit: Int = 20
    ): Observable<TrackSearchResponse>

}