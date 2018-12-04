package io.fgonzaleva.musicforbooks.data.api.model.spotify

import com.google.gson.annotations.SerializedName

data class TokenRequestBody(
    @SerializedName("grant_type")
    val grantType: String = "client_credentials"
)