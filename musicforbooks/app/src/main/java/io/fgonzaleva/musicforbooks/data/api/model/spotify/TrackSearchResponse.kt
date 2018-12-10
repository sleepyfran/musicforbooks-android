package io.fgonzaleva.musicforbooks.data.api.model.spotify

import com.google.gson.annotations.SerializedName

data class TrackSearchResponse(
    @SerializedName("tracks")
    val tracks: List<Track>
) {

    data class Items(
        @SerializedName("items")
        val items: List<Track>
    )

}