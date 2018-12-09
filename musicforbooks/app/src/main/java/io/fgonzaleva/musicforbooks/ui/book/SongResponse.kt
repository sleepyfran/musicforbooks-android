package io.fgonzaleva.musicforbooks.ui.book

import io.fgonzaleva.musicforbooks.data.repositories.model.Song

sealed class SongResponse {

    object Loading : SongResponse()

    object NoResults : SongResponse()

    data class Success(
        val songs: List<Song>
    ): SongResponse()

    data class Error(
        val error: Throwable
    ): SongResponse()

}