package io.fgonzaleva.musicforbooks.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.lapism.searchview.Search
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import io.fgonzaleva.musicforbooks.ui.book.BookActivity
import io.fgonzaleva.musicforbooks.ui.components.BookListAdapter
import io.fgonzaleva.musicforbooks.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment(), Search.OnQueryTextListener {

    private val viewModel by viewModel<DashboardViewModel>()
    private val adapter: BookListAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search.setOnQueryTextListener(this)
        adapter.onBookClick = {
            val bookDetailsIntent = BookActivity.createIntent(context!!, it.bookTitle, it.goodReadsId)
            startActivity(bookDetailsIntent)
        }

        viewModel.feedData.observe(this, Observer { response ->
            when (response) {
                is DashboardResponse.Loading -> showLoading()
                is DashboardResponse.Success -> showData(response.feedData)
                is DashboardResponse.Error -> showError()
            }
        })

        feedRecommendations.adapter = adapter
        feedRecommendations.layoutManager = LinearLayoutManager(context)

        viewModel.loadFeed()
    }

    private fun showLoading() {
        hideFeed()
        hideError()
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
    }

    private fun showData(feedData: List<BookItem>) {
        hideLoading()
        adapter.updateContent(feedData.toMutableList())
        showFeed()
    }

    private fun showFeed() {
        hideLoading()
        hideError()
        feedRecommendations.visibility = View.VISIBLE
    }

    private fun hideFeed() {
        feedRecommendations.visibility = View.GONE
    }

    private fun showError() {
        hideLoading()
        hideFeed()
        error.visibility = View.VISIBLE
    }

    private fun hideError() {
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
