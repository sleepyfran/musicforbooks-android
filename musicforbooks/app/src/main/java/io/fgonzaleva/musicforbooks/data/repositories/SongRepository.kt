package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.api.interfaces.MusicForBooksService
import io.fgonzaleva.musicforbooks.data.api.interfaces.SpotifyService
import io.fgonzaleva.musicforbooks.data.api.model.musicforbooks.BookRequest
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
            .flatMap { response ->
                Single.just(idsFromList(response.spotifyIds))
            }
            .flatMap { ids ->
                spotifyTokenRepository
                    .getTokenOrGenerate()
                    .map { Pair(ids, it.token) }
            }
            .flatMap { (ids, token) ->
                getSpotifyTracks(ids, token)
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

    override fun updateBookWithSong(bookId: Int, song: Song): Single<List<Song>> {
        return musicForBooksService
            .updateBook(BookRequest(bookId, listOf(song.id)))
            .subscribeOn(Schedulers.io())
            .flatMap { response ->
                Single.just(idsFromList(response.spotifyIds))
            }
            .flatMap { ids ->
                spotifyTokenRepository
                    .getTokenOrGenerate()
                    .map { Pair(ids, it.token) }
            }
            .flatMap { (ids, token) ->
                getSpotifyTracks(ids, token)
            }
    }

    private fun getSpotifyTracks(ids: String, token: String): Single<List<Song>> {
        return spotifyService
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
                                        track: TrackResponse,
                                        features: TrackFeaturesResponse.TrackFeatures ->

                                    Song.fromTrackResponse(track, features)
                                }
                            )
                    }
            }
            .toList()
    }

    private fun idsFromList(ids: List<String>): String {
        return ids.reduce { acc, id -> "$acc,$id" }
    }
}