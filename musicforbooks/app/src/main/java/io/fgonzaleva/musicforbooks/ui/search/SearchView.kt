package io.fgonzaleva.musicforbooks.ui.search

import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import io.fgonzaleva.musicforbooks.ui.base.View

interface SearchView : View {
    fun showLoading()
    fun hideLoading()
    fun showResults()
    fun hideResults()
    fun populateResults(results: List<BookItem>)
}