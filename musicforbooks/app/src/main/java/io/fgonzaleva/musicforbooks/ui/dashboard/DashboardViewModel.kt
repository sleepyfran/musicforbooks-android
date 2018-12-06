package io.fgonzaleva.musicforbooks.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.FeedRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class DashboardViewModel(
    private val feedRepository: FeedRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val feedData = MutableLiveData<DashboardResponse>()

    fun loadFeed() {
        val feedDisposable = feedRepository
            .getFeed()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { feedData.value = DashboardResponse.Loading }
            .subscribe(
                {
                    feedData.value = DashboardResponse.Success(it)
                },
                {
                    feedData.value = DashboardResponse.Error(it)
                }
            )

        disposables.add(feedDisposable)
    }

}