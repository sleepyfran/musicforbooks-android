package io.fgonzaleva.musicforbooks.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.fgonzaleva.musicforbooks.R
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.content_search.*

class SearchActivity : AppCompatActivity() {

    companion object {
        const val SEARCH_QUERY_EXTRA = "search_query_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(toolbar)

        val searchQuery = intent.extras?.getString(SEARCH_QUERY_EXTRA)
                          ?: throw IllegalArgumentException("A search_query_extra is needed for the activity to work")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = searchQuery

        val pagerAdapter = SearchPagerAdapter(supportFragmentManager, this, searchQuery)
        searchPager.adapter = pagerAdapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
