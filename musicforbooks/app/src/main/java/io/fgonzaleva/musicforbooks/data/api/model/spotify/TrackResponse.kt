package io.fgonzaleva.musicforbooks.data.api.model.spotify

import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName("tracks")
    val tracks: List<Track>
)