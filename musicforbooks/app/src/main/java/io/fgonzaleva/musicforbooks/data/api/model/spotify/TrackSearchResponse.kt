package io.fgonzaleva.musicforbooks.data.api.model.spotify

import com.google.gson.annotations.SerializedName

data class TrackSearchResponse(
    @SerializedName("tracks")
    val tracks: Tracks
) {

    data class Tracks(
        @SerializedName("items")
        val items: List<Track>
    )

}