package io.fgonzaleva.musicforbooks.ui.book

import io.fgonzaleva.musicforbooks.data.repositories.model.Book
import io.fgonzaleva.musicforbooks.ui.base.View

interface BookView : View {
    fun showLoading()
    fun hideLoading()
    fun showContent()
    fun hideContent()
    fun showBook(book: Book)
}