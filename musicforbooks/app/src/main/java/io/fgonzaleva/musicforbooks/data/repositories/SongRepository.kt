package io.fgonzaleva.musicforbooks.data.repositories

import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SongRepository
import io.fgonzaleva.musicforbooks.data.repositories.model.Song
import io.reactivex.Single

class SongRepository : SongRepository {

    override fun getSongsForBook(bookId: Int): Single<List<Song>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}