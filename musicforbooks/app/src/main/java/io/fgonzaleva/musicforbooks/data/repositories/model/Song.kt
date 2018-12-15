package io.fgonzaleva.musicforbooks.data.repositories.model

import io.fgonzaleva.musicforbooks.data.api.model.spotify.TrackResponse
import io.fgonzaleva.musicforbooks.data.api.model.spotify.TrackFeaturesResponse

data class Song(
    val id: String,
    val title: String,
    val artist: String,
    val isInstrumental: Boolean,
    val score: Int
) {

    companion object {
        fun fromTrackResponse(track: TrackResponse, features: TrackFeaturesResponse.TrackFeatures): Song {
            return Song(
                track.id,
                track.title,
                track.artists.first().name,
                features.instrumentalness > 0.5,
                100
            )
        }

        fun fromTrackResponse(track: TrackResponse): Song {
            return Song(
                track.id,
                track.title,
                track.artists.first().name,
                false,
                0
            )
        }
    }

}