package io.fgonzaleva.musicforbooks.data.repositories.model

data class Book(
    val title: String,
    val authorName: String,
    val coverUrl: String,
    val rating: Float,
    val goodReadsUrl: String
)