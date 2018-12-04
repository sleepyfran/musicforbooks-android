package io.fgonzaleva.musicforbooks.data.repositories.interfaces

import io.fgonzaleva.musicforbooks.data.repositories.model.SpotifyToken
import io.reactivex.Single

interface SpotifyTokenRepository {

    fun getTokenOrGenerate(): Single<SpotifyToken>

}