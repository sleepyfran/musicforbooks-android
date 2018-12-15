package io.fgonzaleva.musicforbooks.ui.book

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.Book
import io.fgonzaleva.musicforbooks.ui.addsong.AddSongActivity

import kotlinx.android.synthetic.main.activity_book.*

class BookActivity : AppCompatActivity() {

    companion object {
        const val BOOK_ID_EXTRA = "book_id_extra"
        const val BOOK_TITLE_EXTRA = "book_title_extra"

        fun createIntent(context: Context, bookTitle: String, bookId: Int): Intent {
            val intent = Intent(context, BookActivity::class.java)
            intent.putExtra(BOOK_TITLE_EXTRA, bookTitle)
            intent.putExtra(BOOK_ID_EXTRA, bookId)
            return intent
        }
    }

    private var bookId: Int = 0
    private lateinit var fragment: BookFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        bookId = intent.extras?.getInt(BOOK_ID_EXTRA)
                     ?: throw IllegalArgumentException("A book_id_extra is required for the activity to work")
        val bookTitle = intent.extras?.getString(BOOK_TITLE_EXTRA)
                        ?: throw IllegalArgumentException("A book_title_extra is required for the activity to work")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = bookTitle

        val onBookLoaded = { book: Book ->
            supportActionBar?.title = book.title
        }

        val onEmptyResults = {
            fab.hide()
        }

        fragment = BookFragment.create(bookId, onBookLoaded, onEmptyResults)
        supportFragmentManager.transaction {
            replace(R.id.fragment, fragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_book, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_add_new_song -> {
            val searchIntent = Intent(this, AddSongActivity::class.java)
            searchIntent.putExtra(AddSongActivity.BOOK_ID_EXTRA, bookId)
            startActivityForResult(searchIntent, AddSongActivity.SONGS_UPDATED)
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AddSongActivity.SONGS_UPDATED && resultCode == Activity.RESULT_OK) {
            fragment.refreshContent()
        }
    }

}
