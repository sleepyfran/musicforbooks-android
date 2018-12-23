package io.fgonzaleva.musicforbooks.ui.search

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import io.fgonzaleva.musicforbooks.R

class SearchPagerAdapter(
    fragmentManager: FragmentManager,
    private val context: Context,
    private val query: String
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when(position) {
            1 -> SearchFragment.create(query, SearchType.SONG)
            else -> SearchFragment.create(query, SearchType.BOOK)
        }
    }

    override fun getPageTitle(position: Int): CharSequence = when (position) {
        1 -> context.getString(R.string.search_by_song)
        else -> context.getString(R.string.search_by_book)
    }

    override fun getCount(): Int = 2

}