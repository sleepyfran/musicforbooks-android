package io.fgonzaleva.musicforbooks.ui.book

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import io.fgonzaleva.musicforbooks.R
import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val bookId = intent.extras?.getInt(BOOK_ID_EXTRA)
                     ?: throw IllegalArgumentException("A book_id_extra is required for the activity to work")
        val bookTitle = intent.extras?.getString(BOOK_TITLE_EXTRA)
                        ?: throw IllegalArgumentException("A book_title_extra is required for the activity to work")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = bookTitle

        val onBookLoaded = { book: BookItem ->
            supportActionBar?.title = book.bookTitle
        }

        val fragment = BookFragment.create(bookId, onBookLoaded)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment, fragment)
            .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
