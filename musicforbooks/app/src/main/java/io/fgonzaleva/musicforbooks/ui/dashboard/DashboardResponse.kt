package io.fgonzaleva.musicforbooks.ui.dashboard

import io.fgonzaleva.musicforbooks.data.repositories.model.BookItem

sealed class DashboardResponse {

    object Loading : DashboardResponse()

    data class Success(
        val feedData: List<BookItem>
    ): DashboardResponse()

    data class Error(
        val error: Throwable
    ): DashboardResponse()

}