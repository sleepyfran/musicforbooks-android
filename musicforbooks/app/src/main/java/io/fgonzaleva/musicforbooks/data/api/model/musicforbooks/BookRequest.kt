package io.fgonzaleva.musicforbooks.data.api.model.musicforbooks

import com.google.gson.annotations.SerializedName

data class BookRequest(
    @SerializedName("goodReadsId")
    val goodReadsId: Int,

    @SerializedName("spotifyIds")
    val spotifyIds: List<String>
)