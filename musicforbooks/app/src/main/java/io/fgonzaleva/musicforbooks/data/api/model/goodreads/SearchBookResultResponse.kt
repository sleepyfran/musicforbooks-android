package io.fgonzaleva.musicforbooks.data.api.model.goodreads

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "GoodreadsResponse")
data class SearchBookResultResponse(
    @Path("search/results")
    @Element(name = "work")
    val results: List<SearchBookResultItem>
)

@Xml(name = "work")
data class SearchBookResultItem(
    @Path("best_book")
    @PropertyElement(name = "id")
    val goodReadsId: Int,

    @Path("best_book")
    @PropertyElement(name = "title")
    val title: String,

    @Path("best_book")
    @Element(name = "author")
    val author: Author,

    @Path("best_book")
    @PropertyElement(name = "image_url")
    val imageUrl: String
)