package io.fgonzaleva.musicforbooks.data.api.model

import com.google.gson.annotations.SerializedName

data class FeedItemResponse(
    @SerializedName("author")
    val authorName: String,
    @SerializedName("title")
    val bookTitle: String,
    @SerializedName("goodReadsId")
    val goodReadsId: Int,
    @SerializedName("coverUrl")
    val coverUrl: String
)