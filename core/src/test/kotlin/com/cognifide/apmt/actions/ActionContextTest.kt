package com.cognifide.apmt.actions

import com.cognifide.apmt.MOCK_SERVER
import com.cognifide.apmt.TEST_USER
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUsers
import com.github.tomakehurst.wiremock.client.BasicCredentials
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.Test

class ActionContextTest {

    @Test
    @AemStub
    fun basicRequestSpecHasCsrfToken() {
        registerUsers(TEST_USER)
        stubFor(
            get(urlPathEqualTo("/"))
                .withBasicAuth(TEST_USER.username, TEST_USER.password)
                .willReturn(ok())
        )

        ActionContext.basicRequestSpec(TEST_USER, MOCK_SERVER).get("/")
            .then().assertThat().statusCode(200)

        verify(
            getRequestedFor(urlPathEqualTo(CSRF_ENDPOINT))
                .withBasicAuth(BasicCredentials(TEST_USER.username, TEST_USER.password))
        )

        verify(
            getRequestedFor(urlPathEqualTo("/"))
                .withHeader(CSRF_TOKEN, equalTo(TEST_USER.username))
                .withBasicAuth(BasicCredentials(TEST_USER.username, TEST_USER.password))
        )
    }
}
