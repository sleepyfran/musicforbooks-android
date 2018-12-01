package io.fgonzaleva.musicforbooks.ui.book

import io.fgonzaleva.musicforbooks.data.repositories.interfaces.BookRepository
import io.fgonzaleva.musicforbooks.ui.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class BookPresenter : BasePresenter<BookView>(), KoinComponent {

    private val bookRepository: BookRepository by inject()

    fun loadBook(bookId: Int) {
        view?.hideContent()
        view?.showLoading()

        val bookDisposable = bookRepository
            .getBook(bookId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    view?.hideLoading()
                    view?.showContent()
                    view?.showBook(it)
                },
                {
                    view?.hideLoading()
                    view?.showError()
                }
            )

        addDisposable(bookDisposable)
    }

}