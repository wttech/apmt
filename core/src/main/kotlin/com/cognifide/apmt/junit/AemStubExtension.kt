package com.cognifide.apmt.junit

import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class AemStubExtension : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        getWireMockServer(context).start()

        WireMock.stubFor(
            WireMock.get(WireMock.urlPathEqualTo("/libs/granite/csrf/token.json"))
                .willReturn(WireMock.okJson("{ \"token\": \"csrftoken\" }"))
        )
    }

    override fun afterEach(context: ExtensionContext?) {
        getWireMockServer(context).stop()
    }
}
