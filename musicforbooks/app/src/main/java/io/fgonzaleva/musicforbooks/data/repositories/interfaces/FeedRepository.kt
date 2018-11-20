package io.fgonzaleva.musicforbooks.data.repositories.interfaces

import io.fgonzaleva.musicforbooks.data.repositories.model.FeedItem
import io.reactivex.Single

interface FeedRepository {
    fun getFeed(): Single<List<FeedItem>>
}