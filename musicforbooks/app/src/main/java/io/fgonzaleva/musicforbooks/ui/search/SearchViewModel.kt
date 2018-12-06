package io.fgonzaleva.musicforbooks.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val searchData = MutableLiveData<SearchResponse>()

    fun loadResults(query: String) {
        val searchDisposable = searchRepository
            .searchBook(query)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { searchData.value = SearchResponse.Loading }
            .subscribe(
                {
                    searchData.value = SearchResponse.Success(it)
                },
                {
                    searchData.value = SearchResponse.Error(it)
                }
            )

        disposables.add(searchDisposable)
    }

}