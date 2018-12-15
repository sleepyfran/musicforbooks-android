package io.fgonzaleva.musicforbooks.ui.addsong

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.transaction
import com.lapism.searchview.Search
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.Song
import kotlinx.android.synthetic.main.activity_add_song.*

class AddSongActivity : AppCompatActivity(), Search.OnQueryTextListener {

    companion object {
        const val BOOK_ID_EXTRA = "book_id_extra"
        const val SONGS_UPDATED = 1
    }

    private lateinit var fragment: AddSongFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_song)

        val bookId = intent.extras?.getInt(BOOK_ID_EXTRA)
            ?: throw IllegalArgumentException("A book_id_extra is required for the activity to work")

        val onSongsUpdate: (songs: List<Song>) -> Unit = {
            setResult(Activity.RESULT_OK)
            finish()
        }

        fragment = AddSongFragment.create(bookId, onSongsUpdate)

        supportFragmentManager.transaction {
            replace(R.id.fragment, fragment)
        }

        songSearch.setOnQueryTextListener(this)
        songSearch.setOnLogoClickListener {
            onBackPressed()
        }
    }

    override fun onQueryTextSubmit(query: CharSequence): Boolean {
        fragment.searchResultsFor(query.toString())
        hideKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: CharSequence?) { }

    private fun hideKeyboard() {
        val inputManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}
