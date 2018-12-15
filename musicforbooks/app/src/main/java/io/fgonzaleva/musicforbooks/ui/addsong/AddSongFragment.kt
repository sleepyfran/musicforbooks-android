package io.fgonzaleva.musicforbooks.ui.addsong

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.Song
import io.fgonzaleva.musicforbooks.ui.components.SongListAdapter
import kotlinx.android.synthetic.main.fragment_add_song.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddSongFragment : Fragment() {

    companion object {
        fun create(
            bookId: Int,
            onSongsUpdate: (songs: List<Song>) -> Unit
        ) = AddSongFragment().apply {
            this.bookId = bookId
            this.onSongsUpdate = onSongsUpdate
        }
    }

    private val viewModel by viewModel<AddSongViewModel>()
    private val adapter: SongListAdapter by inject()
    private var bookId: Int = 0
    private lateinit var onSongsUpdate: (songs: List<Song>) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.shouldShowScore = false
        adapter.onSongClick = { song -> showConfirmationDialog(song) }

        results.adapter = adapter
        results.layoutManager = LinearLayoutManager(context)

        viewModel.addSongData.observe(this, Observer { response: AddSongResponse ->
            when (response) {
                is AddSongResponse.Loading -> showLoading()
                is AddSongResponse.NoResults -> showNoResults()
                is AddSongResponse.Results -> showContent(response.songs)
                is AddSongResponse.Success -> onSongsUpdate(response.songs)
                is AddSongResponse.Error -> showError()
            }
        })

        showStartMessage()
    }

    fun searchResultsFor(query: String) {
        viewModel.searchResultsFor(query)
    }

    private fun showConfirmationDialog(song: Song) {
        val dialogMessage = getString(R.string.song_add_confirmation_message, song.title)

        AlertDialog
            .Builder(context!!)
            .setTitle(R.string.song_add_confirmation_title)
            .setMessage(dialogMessage)
            .setPositiveButton(android.R.string.yes) { _, _ -> viewModel.addSongToBook(bookId, song) }
            .setNegativeButton(android.R.string.no) { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun showStartMessage() {
        hideLoading()
        hideContent()
        hideNoResults()
        hideError()
        searchMessageContent.visibility = View.VISIBLE
    }

    private fun hideStartMessage() {
        searchMessageContent.visibility = View.GONE
    }

    private fun showLoading() {
        hideStartMessage()
        hideContent()
        hideNoResults()
        hideError()
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
    }

    private fun showContent(items: List<Song>) {
        hideStartMessage()
        hideLoading()
        hideNoResults()
        hideError()
        results.visibility = View.VISIBLE

        adapter.updateContent(items.toMutableList())
    }

    private fun hideContent() {
        results.visibility = View.GONE
    }

    private fun showNoResults() {
        hideStartMessage()
        hideLoading()
        hideContent()
        hideError()
        emptyContent.visibility = View.VISIBLE
    }

    private fun hideNoResults() {
        emptyContent.visibility = View.GONE
    }

    private fun showError() {

    }

    private fun hideError() {

    }

}
