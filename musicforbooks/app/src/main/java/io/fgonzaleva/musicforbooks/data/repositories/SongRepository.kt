package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.api.interfaces.MusicForBooksService
import io.fgonzaleva.musicforbooks.data.api.interfaces.SpotifyService
import io.fgonzaleva.musicforbooks.data.api.model.spotify.TrackResponse
import io.fgonzaleva.musicforbooks.data.api.model.spotify.TrackFeaturesResponse
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SongRepository
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SpotifyTokenRepository
import io.fgonzaleva.musicforbooks.data.repositories.model.Song
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class SongRepository(
    private val musicForBooksService: MusicForBooksService,
    private val spotifyService: SpotifyService,
    private val spotifyTokenRepository: SpotifyTokenRepository
) : SongRepository {

    override fun getSongsForBook(bookId: Int): Single<List<Song>> {
        return musicForBooksService
            .getBookSongs(bookId)
            .subscribeOn(Schedulers.io())
            .flatMap {  response ->
                spotifyTokenRepository
                    .getTokenOrGenerate()
                    .flatMap {  credentials ->
                        val ids = if (response.spotifyIds.isNotEmpty()) {
                            response.spotifyIds.reduce { acc, id -> "$acc,$id" }
                        } else {
                            ""
                        }

                        spotifyService
                            .getTracks(
                                credentials.token,
                                ids
                            )
                            .flatMap { trackResponse ->
                                spotifyService
                                    .getTracksFeatures(
                                        credentials.token,
                                        ids
                                    )
                                    .flatMap { featuresResponse ->
                                        Observable
                                            .fromIterable(trackResponse.tracks)
                                            .zipWith(
                                                Observable.fromIterable(featuresResponse.features),
                                                BiFunction {
                                                        track: TrackResponse.Track,
                                                        features: TrackFeaturesResponse.TrackFeatures ->

                                                    Song.fromTrackResponses(track, features)
                                                }
                                            )
                                    }
                            }
                            .toList()
                    }
            }

    }

}