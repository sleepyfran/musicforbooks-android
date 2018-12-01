package io.fgonzaleva.musicforbooks.ui.book

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.Book
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import kotlinx.android.synthetic.main.fragment_book.*
import org.koin.android.ext.android.inject
import org.koin.standalone.KoinComponent

class BookFragment : Fragment(), BookView, KoinComponent {

    companion object {
        fun create(bookId: Int, onBookLoaded: (book: BookItem) -> Unit) =
            BookFragment().apply {
                this.bookId = bookId
                this.onBookLoaded = onBookLoaded
            }
    }

    private val presenter: BookPresenter by inject()
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
        presenter.attach(this)
        presenter.loadBook(bookId)
    }

    override fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading.visibility = View.GONE
    }

    override fun showBook(book: Book) {
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
    }

    override fun showContent() {
        content.visibility = View.VISIBLE
    }

    override fun hideContent() {
        content.visibility = View.GONE
    }

    override fun showError() {
        error.visibility = View.VISIBLE
    }

    override fun hideError() {
        error.visibility = View.GONE
    }
}