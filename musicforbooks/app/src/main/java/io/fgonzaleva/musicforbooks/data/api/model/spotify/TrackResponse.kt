package io.fgonzaleva.musicforbooks.data.api.model.spotify

import com.google.gson.annotations.SerializedName

data class TrackResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val title: String,
    @SerializedName("artists")
    val artists: List<ArtistResponse>,
    @SerializedName("duration_ms")
    val length: Long
)