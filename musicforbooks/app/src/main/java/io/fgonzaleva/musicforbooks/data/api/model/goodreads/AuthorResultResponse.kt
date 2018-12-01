package io.fgonzaleva.musicforbooks.data.api.model.goodreads

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name = "author")
data class AuthorResultResponse(
    @PropertyElement(name = "id")
    val goodReadsId: Int,

    @PropertyElement(name = "name")
    val name: String
)