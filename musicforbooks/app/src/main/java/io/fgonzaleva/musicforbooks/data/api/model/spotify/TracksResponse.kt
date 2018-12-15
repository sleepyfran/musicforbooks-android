package io.fgonzaleva.musicforbooks.data.api.model.spotify

import com.google.gson.annotations.SerializedName

data class TracksResponse(
    @SerializedName("tracks")
    val tracks: List<TrackResponse>
)