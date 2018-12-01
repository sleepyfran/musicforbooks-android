package io.fgonzaleva.musicforbooks.ui.book

import io.fgonzaleva.musicforbooks.data.repositories.model.Book
import io.fgonzaleva.musicforbooks.ui.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.standalone.KoinComponent
import java.util.concurrent.TimeUnit

class BookPresenter : BasePresenter<BookView>(), KoinComponent {

    fun loadBook(bookId: Int) {
        view?.hideContent()
        view?.showLoading()

        // TODO: Do some real logic here
        Observable
            .just(1)
            .subscribeOn(Schedulers.io())
            .delay(5, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                view?.hideLoading()
                view?.showContent()

                view?.showBook(Book(
                    "The Joke",
                    "Milan Kundera",
                    "https://images.gr-assets.com/books/1477989646l/397281.jpg",
                    3.99f,
                    "https://www.goodreads.com/book/show/397281.The_Joke"
                ))
            }
    }

}