package io.fgonzaleva.musicforbooks.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.lapism.searchview.Search
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.FeedItem
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject

class DashboardFragment : Fragment(), Dashboard.View, Search.OnQueryTextListener {

    private val presenter: DashboardPresenter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)
        presenter.loadFeed()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }

    override fun showFeed() {
        feedRecommendations.visibility = View.VISIBLE
    }

    override fun hideFeed() {
        feedRecommendations.visibility = View.GONE
    }

    override fun populateFeed(items: List<FeedItem>) {

    }

    override fun showError() {
        error.visibility = View.VISIBLE
    }

    override fun hideError() {
        error.visibility = View.GONE
    }

    override fun onQueryTextSubmit(query: CharSequence?): Boolean {
        presenter.handleSearch(query.toString())
        return true
    }

    override fun onQueryTextChange(newText: CharSequence?) { }

}
