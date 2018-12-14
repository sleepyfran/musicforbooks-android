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
    val searchData = MutableLiveData<AddSongResponse>()

    fun searchResultsFor(query: String) {
        val searchDisposable = songRepository
            .searchSongs(query)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { searchData.value = AddSongResponse.Loading }
            .subscribe(
                { results ->
                    searchData.value = AddSongResponse.Success(results)
                },
                { error ->
                    searchData.value = AddSongResponse.Error(error)
                }
            )

        disposables.add(searchDisposable)
    }

    fun addSongToBook(bookId: Int, song: Song) {
        // TODO: Do something here
    }

}
