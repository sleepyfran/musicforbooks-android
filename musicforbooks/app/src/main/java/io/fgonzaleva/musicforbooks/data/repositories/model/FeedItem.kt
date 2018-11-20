package io.fgonzaleva.musicforbooks.data.repositories.model

data class FeedItem(
    val authorName: String,
    val bookTitle: String,
    val goodReadsId: Int,
    val coverUrl: String
)