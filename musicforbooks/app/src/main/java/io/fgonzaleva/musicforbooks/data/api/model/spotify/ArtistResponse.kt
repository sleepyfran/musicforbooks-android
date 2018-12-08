package io.fgonzaleva.musicforbooks.data.api.model.spotify

import com.google.gson.annotations.SerializedName

data class ArtistResponse(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val spotifyId: String
)