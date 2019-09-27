package com.cognifide.apmt.util

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.CSRF_ENDPOINT
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class AemStubExtension : BeforeEachCallback, AfterEachCallback {

    override fun beforeEach(context: ExtensionContext?) {
        getWireMockServer(context).start()
    }

    override fun afterEach(context: ExtensionContext?) {
        getWireMockServer(context).stop()
    }

    companion object {
        fun registerUser(username: String, password: String) {
            stubFor(
                get(urlPathEqualTo(CSRF_ENDPOINT))
                    .withBasicAuth(username, password)
                    .willReturn(okJson("{ \"token\": \"$username\" }"))
            )
        }

        fun registerUsers(vararg users: User) {
            users.forEach { registerUser(it.username, it.password) }
        }
    }
}
