package io.fgonzaleva.musicforbooks.data.repositories.model

data class Song(
    val title: String,
    val artist: String,
    val isInstrumental: Boolean,
    val score: Int
)