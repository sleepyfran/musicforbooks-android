package io.fgonzaleva.musicforbooks.ui.dashboard

import io.fgonzaleva.musicforbooks.data.repositories.model.FeedItem
import io.fgonzaleva.musicforbooks.ui.base.View

interface DashboardView : View {
    fun showLoading()
    fun hideLoading()
    fun showFeed()
    fun hideFeed()
    fun populateFeed(items: List<FeedItem>)
}