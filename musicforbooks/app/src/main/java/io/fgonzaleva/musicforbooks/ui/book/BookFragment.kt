package io.fgonzaleva.musicforbooks.ui.book

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.Book
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import kotlinx.android.synthetic.main.fragment_book.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.standalone.KoinComponent

class BookFragment : Fragment() {

    companion object {
        fun create(bookId: Int, onBookLoaded: (book: BookItem) -> Unit) =
            BookFragment().apply {
                this.bookId = bookId
                this.onBookLoaded = onBookLoaded
            }
    }

    private val viewModel by viewModel<BookViewModel>()
    private var bookId: Int = 0
    private lateinit var onBookLoaded: (book: BookItem) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bookData.observe(this, Observer { response ->
            when (response) {
                is BookResponse.Loading -> showLoading()
                is BookResponse.Success -> showBook(response.book)
                is BookResponse.Error -> showError()
            }
        })

        viewModel.loadBook(bookId)
    }

    fun showLoading() {
        hideContent()
        hideError()
        loading.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loading.visibility = View.GONE
    }

    fun showBook(book: Book) {
        hideLoading()
        hideError()

        bookTitle.text = book.title
        authorName.text = book.authorName
        bookRating.rating = book.rating

        openOnGoodReads.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(book.goodReadsUrl))
            startActivity(browserIntent)
        }

        Glide
            .with(this)
            .load(book.coverUrl)
            .into(bookCover)

        showContent()
    }

    fun showContent() {
        content.visibility = View.VISIBLE
    }

    fun hideContent() {
        content.visibility = View.GONE
    }

    fun showError() {
        hideLoading()
        hideContent()
        error.visibility = View.VISIBLE
    }

    fun hideError() {
        error.visibility = View.GONE
    }
}