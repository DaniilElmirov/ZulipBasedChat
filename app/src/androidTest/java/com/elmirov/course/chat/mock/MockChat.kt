package com.elmirov.course.chat.mock

import com.elmirov.course.util.AssetsUtils.fromAssets
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching

class MockChat(private val wireMockServer: WireMockServer) {
    companion object {
        val messagesUrlPattern = urlPathMatching(".+messages")
        private val topicsUrlPattern = urlPathMatching(".+topics")

        fun WireMockServer.messages(block: MockChat.() -> Unit) {
            MockChat(this).apply(block)
        }
    }

    private val getMessagesMatcher = WireMock.get(messagesUrlPattern)
    private val getTopicsMatcher = WireMock.get(topicsUrlPattern)
    private val sendMessagesMatcher = WireMock.post(messagesUrlPattern)

    fun withAll() {
        wireMockServer.stubFor(getMessagesMatcher.willReturn(ok(fromAssets("chat/messages.json"))))
        wireMockServer.stubFor(getTopicsMatcher.willReturn(ok(fromAssets("chat/topics.json"))))
    }

    fun withSend() {
        wireMockServer.stubFor(getMessagesMatcher.willReturn(ok(fromAssets("chat/messages_with_my.json"))))
        wireMockServer.stubFor(sendMessagesMatcher.willReturn(ok(fromAssets("chat/default_response.json"))))
        wireMockServer.stubFor(getTopicsMatcher.willReturn(ok(fromAssets("chat/topics.json"))))
    }
}