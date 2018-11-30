package io.fgonzaleva.musicforbooks.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.lapism.searchview.Search
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import io.fgonzaleva.musicforbooks.ui.components.BookListAdapter
import io.fgonzaleva.musicforbooks.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject

class DashboardFragment : Fragment(), DashboardView, Search.OnQueryTextListener {

    private val presenter: DashboardPresenter by inject()
    private val adapter: BookListAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attach(this)

        search.setOnQueryTextListener(this)
        adapter.onBookClick = {
            // TODO: Do something with the book item
        }

        feedRecommendations.adapter = adapter
        feedRecommendations.layoutManager = LinearLayoutManager(context)

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

    override fun populateFeed(items: List<BookItem>) {
        adapter.updateContent(items.toMutableList())
    }

    override fun showError() {
        error.visibility = View.VISIBLE
    }

    override fun hideError() {
        error.visibility = View.GONE
    }

    override fun onQueryTextSubmit(query: CharSequence?): Boolean {
        val searchIntent = Intent(activity, SearchActivity::class.java)
        searchIntent.putExtra(SearchActivity.SEARCH_QUERY_EXTRA, query)
        startActivity(searchIntent)

        return true
    }

    override fun onQueryTextChange(newText: CharSequence?) { }

}
