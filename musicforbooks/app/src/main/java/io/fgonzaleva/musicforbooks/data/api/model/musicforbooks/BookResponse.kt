package io.fgonzaleva.musicforbooks.data.api.model.musicforbooks

data class BookResponse(
    val goodReadsId: Int,
    val spotifyIds: List<String>
)