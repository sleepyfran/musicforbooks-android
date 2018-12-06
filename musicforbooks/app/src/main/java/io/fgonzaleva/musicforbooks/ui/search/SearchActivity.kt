package io.fgonzaleva.musicforbooks.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.transaction
import io.fgonzaleva.musicforbooks.R
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    companion object {
        const val SEARCH_QUERY_EXTRA = "search_query_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)

        val searchQuery = intent.extras?.getString(SEARCH_QUERY_EXTRA)!!

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = searchQuery

        val fragment = SearchFragment.create(searchQuery)
        supportFragmentManager.transaction {
            replace(R.id.fragment, fragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
