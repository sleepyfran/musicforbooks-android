package io.fgonzaleva.musicforbooks.data.api.interfaces

import io.fgonzaleva.musicforbooks.data.api.model.spotify.TokenResponse
import io.reactivex.Single
import retrofit2.http.*

interface SpotifyAuthService {

    @FormUrlEncoded
    @POST("token")
    fun getToken(
        @Header("Authorization") authorizationHeader: String,
        @Field("grant_type") grantType: String = "client_credentials"
    ): Single<TokenResponse>

}