package io.fgonzaleva.musicforbooks.ui.book

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.BookRepository
import io.fgonzaleva.musicforbooks.data.repositories.interfaces.SongRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import retrofit2.HttpException

class BookViewModel(
    private val bookRepository: BookRepository,
    private val songRepository: SongRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()
    val bookData = MutableLiveData<BookResponse>()
    val songData = MutableLiveData<SongResponse>()

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

    fun loadSongs(bookId: Int) {
        val songsDisposable = songRepository
            .getSongsForBook(bookId)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { songData.value = SongResponse.Loading }
            .subscribe(
                {
                    songData.value = SongResponse.Success(it)
                },
                {
                    val httpError = it as? HttpException

                    if (httpError?.code() == 404) {
                        songData.value = SongResponse.Success(listOf())
                    } else {
                        songData.value = SongResponse.Error(it)
                    }
                }
            )

        disposables.add(songsDisposable)
    }

}