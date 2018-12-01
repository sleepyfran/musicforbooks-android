package io.fgonzaleva.musicforbooks.data.api.model.goodreads

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.Path
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "GoodreadsResponse")
data class BookResponse(
    @Path("book")
    @PropertyElement(name = "title")
    val title: String,

    @Path("book/authors")
    @Element
    val authors: List<Author>,

    @Path("book")
    @PropertyElement(name = "average_rating")
    val rating: Double,

    @Path("book")
    @PropertyElement(name = "image_url")
    val imageUrl: String,

    @Path("book")
    @PropertyElement(name = "url")
    val goodReadsUrl: String
)

@Xml
data class Author(
    @PropertyElement
    val name: String,

    @PropertyElement
    val id: Int
)