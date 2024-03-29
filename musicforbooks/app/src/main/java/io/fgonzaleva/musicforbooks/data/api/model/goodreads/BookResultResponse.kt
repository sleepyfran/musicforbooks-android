package io.fgonzaleva.musicforbooks.data.api.model.goodreads

import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "work")
data class BookResultResponse(
    @Path("best_book")
    @PropertyElement(name = "id")
    val goodReadsId: Int,

    @Path("best_book")
    @PropertyElement(name = "title")
    val title: String,

    @Path("best_book/author")
    @PropertyElement(name = "id")
    val authorGoodReadsId: Int,

    @Path("best_book/author")
    @PropertyElement(name = "name")
    val authorName: String,

    @Path("best_book")
    @PropertyElement(name = "image_url")
    val imageUrl: String
)