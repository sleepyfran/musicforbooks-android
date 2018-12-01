package io.fgonzaleva.musicforbooks.data.api.model.goodreads

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "GoodreadsResponse")
data class SearchResultResponse(
    @Path("search/results")
    @Element(name = "work")
    val results: List<BookResultResponse>
)