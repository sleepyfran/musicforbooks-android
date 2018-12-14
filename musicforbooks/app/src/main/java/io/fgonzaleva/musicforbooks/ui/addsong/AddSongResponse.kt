package io.fgonzaleva.musicforbooks.ui.addsong

import io.fgonzaleva.musicforbooks.data.repositories.model.Song

sealed class AddSongResponse {

    object Loading : AddSongResponse()

    object NoResults : AddSongResponse()

    data class Success(
        val songs: List<Song>
    ): AddSongResponse()

    data class Error(
        val error: Throwable
    ): AddSongResponse()

}