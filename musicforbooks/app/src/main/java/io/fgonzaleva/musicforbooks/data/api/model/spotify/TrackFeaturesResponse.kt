package io.fgonzaleva.musicforbooks.data.api.model.spotify

import com.google.gson.annotations.SerializedName

data class TrackFeaturesResponse(
    @SerializedName("audio_features")
    val features: List<TrackFeatures>
) {

    data class TrackFeatures(
        @SerializedName("instrumentalness")
        val instrumentalness: Float
    )

}