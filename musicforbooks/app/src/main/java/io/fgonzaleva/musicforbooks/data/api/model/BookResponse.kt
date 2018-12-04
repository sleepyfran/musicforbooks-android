package io.fgonzaleva.musicforbooks.data.api.model

data class BookResponse(
    val goodReadsId: Int,
    val spotifyIds: List<String>
)