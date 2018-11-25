package io.fgonzaleva.musicforbooks.data.repositories.interfaces

import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem
import io.reactivex.Single

interface FeedRepository {
    fun getFeed(): Single<List<BookItem>>
}