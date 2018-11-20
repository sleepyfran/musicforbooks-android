package io.fgonzaleva.musicforbooks.ui.dashboard

import io.fgonzaleva.musicforbooks.data.repositories.model.FeedItem

interface Dashboard {
    interface View : io.fgonzaleva.musicforbooks.ui.base.View {
        fun showLoading()
        fun hideLoading()
        fun showFeed()
        fun hideFeed()
        fun populateFeed(items: List<FeedItem>)
    }
}