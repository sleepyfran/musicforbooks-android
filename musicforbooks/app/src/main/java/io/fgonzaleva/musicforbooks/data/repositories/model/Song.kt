package io.fgonzaleva.musicforbooks.data.repositories.model

import io.fgonzaleva.musicforbooks.data.api.model.spotify.TrackFeaturesResponse
import io.fgonzaleva.musicforbooks.data.api.model.spotify.TrackResponse

data class Song(
    val title: String,
    val artist: String,
    val isInstrumental: Boolean,
    val score: Int
) {

    companion object {
        fun fromTrackResponses(track: TrackResponse.Track, features: TrackFeaturesResponse.TrackFeatures): Song {
            return Song(
                track.title,
                track.artists.first().name,
                features.instrumentalness > 0.5,
                100
            )
        }
    }

}