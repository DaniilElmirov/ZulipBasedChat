package com.elmirov.course.channels.mock

import com.elmirov.course.util.AssetsUtils.fromAssets
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching

class MockChannels(private val wireMockServer: WireMockServer) {
    companion object {
        private val subscribedUrlPattern = urlPathMatching(".+subscriptions")
        private val topicsUrlPattern = urlPathMatching(".+/topics")

        fun WireMockServer.channels(block: MockChannels.() -> Unit) {
            MockChannels(this).apply(block)
        }
    }

    private val subscribedMatcher = WireMock.get(subscribedUrlPattern)
    private val topicsMatcher = WireMock.get(topicsUrlPattern)

    fun withSubscribed() {
        wireMockServer.stubFor(subscribedMatcher.willReturn(ok(fromAssets("channels/subscribed_list.json"))))
        wireMockServer.stubFor(topicsMatcher.willReturn(ok(fromAssets("channels/topics.json"))))
    }
}