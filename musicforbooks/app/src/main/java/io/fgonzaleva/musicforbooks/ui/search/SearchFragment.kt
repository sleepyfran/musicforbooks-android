package io.fgonzaleva.musicforbooks.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import io.fgonzaleva.musicforbooks.ui.book.BookActivity
import io.fgonzaleva.musicforbooks.ui.components.BookListAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    companion object {
        fun create(query: String) =
                SearchFragment().apply {
                    this.query = query
                }
    }

    private val viewModel by viewModel<SearchViewModel>()
    private lateinit var query: String
    private val adapter: BookListAdapter by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.onBookClick = {
            val bookDetailsIntent = BookActivity.createIntent(context!!, it.bookTitle, it.goodReadsId)
            startActivity(bookDetailsIntent)
        }

        viewModel.searchData.observe(this, Observer { response ->
            when (response) {
                is SearchResponse.Loading -> showLoading()
                is SearchResponse.Success -> populateResults(response.results)
                is SearchResponse.Error -> showError()
            }
        })

        resultItems.adapter = adapter
        resultItems.layoutManager = LinearLayoutManager(context)

        viewModel.loadResults(query)
    }

    fun showError() {
        hideLoading()
        hideResults()
        noResultsError.visibility = View.VISIBLE
    }

    fun hideError() {
        noResultsError.visibility = View.GONE
    }

    fun showLoading() {
        hideResults()
        hideError()
        loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loading.visibility = View.GONE
    }

    fun showResults() {
        hideLoading()
        hideError()
        resultItems.visibility = View.VISIBLE
    }

    fun hideResults() {
        resultItems.visibility = View.GONE
    }

    fun populateResults(results: List<BookItem>) {
        adapter.updateContent(results.toMutableList())
        showResults()
    }

}
