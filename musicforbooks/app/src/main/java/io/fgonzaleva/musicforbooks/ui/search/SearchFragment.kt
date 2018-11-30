package io.fgonzaleva.musicforbooks.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import io.fgonzaleva.musicforbooks.ui.components.BookListAdapter
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.ext.android.inject
import org.koin.standalone.KoinComponent

class SearchFragment : Fragment(), SearchView, KoinComponent {

    companion object {
        fun create(query: String) =
                SearchFragment().apply {
                    this.query = query
                }
    }

    private val presenter: SearchPresenter by inject()
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
        presenter.attach(this)

        adapter.onBookClick = {
            // TODO: Do something with the book item
        }

        resultItems.adapter = adapter
        resultItems.layoutManager = LinearLayoutManager(context)

        presenter.loadResults(query)
    }

    override fun showError() {
        noResultsError.visibility = View.VISIBLE
    }

    override fun hideError() {
        noResultsError.visibility = View.GONE
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }

    override fun showResults() {
        resultItems.visibility = View.VISIBLE
    }

    override fun hideResults() {
        resultItems.visibility = View.GONE
    }

    override fun populateResults(results: List<BookItem>) {
        adapter.updateContent(results.toMutableList())
    }

}
