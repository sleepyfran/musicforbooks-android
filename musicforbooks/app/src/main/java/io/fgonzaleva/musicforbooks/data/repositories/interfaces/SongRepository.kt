package io.fgonzaleva.musicforbooks.data.repositories.interfaces

import io.fgonzaleva.musicforbooks.data.repositories.model.Song
import io.reactivex.Single

interface SongRepository {
    fun getSongsForBook(bookId: Int): Single<List<Song>>
    fun searchSongs(query: String): Single<List<Song>>
}