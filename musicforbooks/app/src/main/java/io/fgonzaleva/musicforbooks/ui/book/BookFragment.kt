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
import io.fgonzaleva.musicforbooks.data.repositories.model.Song
import io.fgonzaleva.musicforbooks.ui.components.SongListAdapter
import kotlinx.android.synthetic.main.fragment_book.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookFragment : Fragment() {

    companion object {
        fun create(
            bookId: Int,
            onBookLoaded: (book: Book) -> Unit,
            onEmptyResults: () -> Unit
        ) =
            BookFragment().apply {
                this.bookId = bookId
                this.onBookLoaded = onBookLoaded
                this.onEmptyResults = onEmptyResults
            }
    }

    private val viewModel by viewModel<BookViewModel>()
    private val adapter: SongListAdapter by inject()
    private var bookId: Int = 0
    private lateinit var onBookLoaded: (book: Book) -> Unit
    private lateinit var onEmptyResults: () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        songs.adapter = adapter
        songs.layoutManager = LinearLayoutManager(context)
        adapter.onSongClick = { song ->
            // Handle the song click
        }

        onlyInstrumental.setOnCheckedChangeListener { _, isOnlyInstrumentalsChecked ->
            if (isOnlyInstrumentalsChecked) {
                adapter.applyFilter { it.isInstrumental }
            } else {
                adapter.unapplyFilter()
            }
        }

        viewModel.bookData.observe(this, Observer { response ->
            when (response) {
                is BookResponse.Loading -> showGeneralLoading()
                is BookResponse.Success -> handleBook(response.book)
                is BookResponse.Error -> showError()
            }
        })

        viewModel.songData.observe(this, Observer { response ->
            when (response) {
                is SongResponse.Loading -> showSongLoading()
                is SongResponse.Success -> showSongs(response.songs)
                is SongResponse.NoResults -> showNoSongs()
                is SongResponse.Error -> showError()
            }
        })

        viewModel.loadBook(bookId)
        viewModel.loadSongs(bookId)
    }

    private fun showGeneralLoading() {
        hideGeneralContent()
        hideError()
        loading.visibility = View.VISIBLE
    }

    private fun hideGeneralLoading() {
        loading.visibility = View.GONE
    }

    private fun showSongLoading() {
        hideSongsContent()
        songsLoading.visibility = View.VISIBLE
    }

    private fun hideSongLoading() {
        songsLoading.visibility = View.GONE
    }

    private fun handleBook(book: Book) {
        onBookLoaded(book)
        showBook(book)
    }

    private fun showBook(book: Book) {
        hideGeneralLoading()
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

        showGeneralContent()
    }

    private fun showSongs(songs: List<Song>) {
        showSongsContent()
        adapter.updateContent(songs.toMutableList())
    }

    private fun showGeneralContent() {
        content.visibility = View.VISIBLE
    }

    private fun hideGeneralContent() {
        content.visibility = View.GONE
    }

    private fun showSongsContent() {
        hideNoSongs()
        hideSongLoading()
        songsContent.visibility = View.VISIBLE
    }

    private fun hideSongsContent() {
        songsContent.visibility = View.GONE
    }

    private fun showNoSongs() {
        hideSongLoading()
        hideSongsContent()
        onEmptyResults()
        emptyContent.visibility = View.VISIBLE
    }

    private fun hideNoSongs() {
        emptyContent.visibility = View.GONE
    }

    private fun showError() {
        hideGeneralLoading()
        hideGeneralContent()
        error.visibility = View.VISIBLE
    }

    private fun hideError() {
        error.visibility = View.GONE
    }
}