package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.api.interfaces.MusicForBooksService
import io.fgonzaleva.musicforbooks.data.api.interfaces.SpotifyService
import io.fgonzaleva.musicforbooks.data.api.model.spotify.Track
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
            .flatMap { response ->
                val ids = response.spotifyIds.reduce { acc, id -> "$acc,$id" }
                Single.just(ids)
            }
            .flatMap { ids ->
                spotifyTokenRepository
                    .getTokenOrGenerate()
                    .map { Pair(ids, it.token) }
            }
            .flatMap { (ids, token) ->
                spotifyService
                    .getTracks(
                        token,
                        ids
                    )
                    .flatMap { trackResponse ->
                        spotifyService
                            .getTracksFeatures(
                                token,
                                ids
                            )
                            .flatMap { featuresResponse ->
                                Observable
                                    .fromIterable(trackResponse.tracks)
                                    .zipWith(
                                        Observable.fromIterable(featuresResponse.features),
                                        BiFunction {
                                                track: Track,
                                                features: TrackFeaturesResponse.TrackFeatures ->

                                            Song.fromTrackResponse(track, features)
                                        }
                                    )
                            }
                    }
                    .toList()
            }
    }

    override fun searchSongs(query: String): Single<List<Song>> {
        return spotifyTokenRepository
            .getTokenOrGenerate()
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.token
            }
            .flatMap {  token ->
                spotifyService
                    .search(
                        token,
                        query,
                        limit = 50
                    )
                    .flatMap { response ->
                        Observable
                            .fromIterable(response.tracks.items)
                            .map { track ->
                                Song.fromTrackResponse(track)
                            }
                    }
                    .toList()
            }
    }

}