package com.cognifide.apmt.util

import com.cognifide.apmt.TEST_USER
import com.cognifide.apmt.actions.CSRF_ENDPOINT
import com.github.tomakehurst.wiremock.client.WireMock
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class AemStubExtension : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        getWireMockServer(context).start()

        WireMock.stubFor(
            WireMock.get(WireMock.urlPathEqualTo(CSRF_ENDPOINT))
                .withBasicAuth(TEST_USER.username, TEST_USER.password)
                .willReturn(WireMock.okJson("{ \"token\": \"CORRECT_TOKEN\" }"))
        )
    }

    override fun afterEach(context: ExtensionContext?) {
        getWireMockServer(context).stop()
    }
}
