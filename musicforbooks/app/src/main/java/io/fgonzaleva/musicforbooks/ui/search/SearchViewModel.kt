package io.fgonzaleva.musicforbooks.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SearchRepository
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val bookSearchData = MutableLiveData<SearchResponse>()

    private fun commonLoad(search: Single<List<BookItem>>) {
        val searchDisposable = search
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { bookSearchData.value = SearchResponse.Loading }
            .subscribe(
                {
                    bookSearchData.value = if (it.isEmpty()) {
                        SearchResponse.NoResults
                    } else {
                        SearchResponse.Success(it)
                    }
                },
                {
                    bookSearchData.value = SearchResponse.Error(it)
                }
            )

        disposables.add(searchDisposable)
    }

    fun loadBookResults(query: String) {
        commonLoad(searchRepository.searchBook(query))
    }

    fun loadSongResults(query: String) {
        commonLoad(searchRepository.searchSong(query))
    }

}