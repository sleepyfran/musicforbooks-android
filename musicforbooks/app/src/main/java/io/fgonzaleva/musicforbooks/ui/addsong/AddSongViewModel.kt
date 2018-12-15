package io.fgonzaleva.musicforbooks.ui.addsong

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SongRepository
import io.fgonzaleva.musicforbooks.data.repositories.model.Song
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class AddSongViewModel(
    private val songRepository: SongRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val addSongData = MutableLiveData<AddSongResponse>()

    fun searchResultsFor(query: String) {
        val searchDisposable = songRepository
            .searchSongs(query)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { addSongData.value = AddSongResponse.Loading }
            .subscribe(
                { results ->
                    addSongData.value = AddSongResponse.Results(results)
                },
                { error ->
                    addSongData.value = AddSongResponse.Error(error)
                }
            )

        disposables.add(searchDisposable)
    }

    fun addSongToBook(bookId: Int, song: Song) {
        val updateDisposable = songRepository
            .updateBookWithSong(bookId, song)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { updatedSongs ->
                    addSongData.value = AddSongResponse.Success(updatedSongs)
                },
                { error ->
                    addSongData.value = AddSongResponse.Error(error)
                }
            )

        disposables.add(updateDisposable)
    }

}
