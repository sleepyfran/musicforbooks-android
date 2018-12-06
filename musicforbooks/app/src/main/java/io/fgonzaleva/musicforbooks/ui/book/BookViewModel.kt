package io.fgonzaleva.musicforbooks.ui.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.BookRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class BookViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val bookData = MutableLiveData<BookResponse>()

    fun loadBook(bookId: Int) {
        val bookDisposable = bookRepository
            .getBook(bookId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { bookData.value = BookResponse.Loading }
            .subscribe(
                {
                    bookData.value = BookResponse.Success(it)
                },
                {
                    bookData.value = BookResponse.Error(it)
                }
            )

        disposables.add(bookDisposable)
    }

}