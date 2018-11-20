package io.fgonzaleva.musicforbooks.ui.dashboard

import android.util.Log
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.FeedRepository
import io.fgonzaleva.musicforbooks.ui.base.BasePresenter
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class DashboardPresenter : BasePresenter<Dashboard.View>(), KoinComponent {

    private val feedRepository: FeedRepository by inject()

    fun loadFeed() {
        view?.hideFeed()
        view?.showLoading()

        val feed = feedRepository
            .getFeed()
            .subscribe(
                {
                    view?.hideLoading()
                    view?.showFeed()
                    view?.populateFeed(it)
                },
                {
                    view?.hideLoading()
                    view?.showError()
                }
            )

        addDisposable(feed)
    }

    fun handleSearch(query: String) {
        Log.i(javaClass.name, "Called search with query $query")
    }

}