package io.fgonzaleva.musicforbooks.ui.dashboard

import android.util.Log
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.FeedRepository
import io.fgonzaleva.musicforbooks.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class DashboardPresenter : BasePresenter<DashboardView>(), KoinComponent {

    private val feedRepository: FeedRepository by inject()

    fun loadFeed() {
        view?.hideFeed()
        view?.showLoading()

        val feed = feedRepository
            .getFeed()
            .observeOn(AndroidSchedulers.mainThread())
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

}