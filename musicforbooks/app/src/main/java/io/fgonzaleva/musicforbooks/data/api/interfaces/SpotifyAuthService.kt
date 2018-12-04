package io.fgonzaleva.musicforbooks.data.api.interfaces

import io.fgonzaleva.musicforbooks.data.api.model.spotify.TokenRequestBody
import io.fgonzaleva.musicforbooks.data.api.model.spotify.TokenResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface SpotifyAuthService {

    @POST("/token")
    fun getToken(
        @Header("Authorization") authorizationHeader: String,
        @Body() tokenRequestBody: TokenRequestBody
    ): Single<TokenResponse>

}