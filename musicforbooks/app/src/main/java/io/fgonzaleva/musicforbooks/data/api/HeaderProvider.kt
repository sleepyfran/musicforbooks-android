package io.fgonzaleva.musicforbooks.data.api

import org.apache.commons.codec.binary.Base64
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class HeaderProvider : KoinComponent {

    private val credentialsProvider: CredentialsProvider by inject()

    fun generateSpotifyHeader(): String {
        val clientIds = "${credentialsProvider.spotifyClientId}:${credentialsProvider.spotifyClientSecret}"
        val encodedClients = String(Base64.encodeBase64(clientIds.toByteArray()))
        return "Basic: $encodedClients"
    }

}