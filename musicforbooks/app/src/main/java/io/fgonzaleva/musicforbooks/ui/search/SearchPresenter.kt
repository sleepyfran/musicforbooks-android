package io.fgonzaleva.musicforbooks.ui.search

import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SearchRepository
import io.fgonzaleva.musicforbooks.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SearchPresenter : BasePresenter<SearchView>(), KoinComponent {

    private val searchRepository: SearchRepository by inject()

    fun loadResults(query: String) {
        view?.hideResults()
        view?.showLoading()

        val search = searchRepository
            .searchBook(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.hideLoading()
                    view?.showResults()
                    view?.populateResults(it)
                },
                {
                    view?.hideLoading()
                    view?.showError()
                }
            )

        addDisposable(search)
    }

}