package com.cognifide.apmt.tests.page

import com.cognifide.apmt.actions.CSRF_TOKEN
import com.cognifide.apmt.tests.ApmtTestCases
import com.cognifide.apmt.tests.ApmtUsers
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUser
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUsers
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeEach

@AemStub
class ApmtEditPagePropertiesTest : EditPagePropertiesTest(
    ApmtTestCases.EDIT_PAGE_PROPERTIES
) {

    @BeforeEach
    fun beforeEach() {
        registerUser("admin", "admin")
        registerUsers(*ApmtUsers.values())

        stubFor(
            post(urlPathEqualTo("/content/my-site/en_gl/home/jcr%3Acontent"))
                .willReturn(aResponse().withStatus(200))
        )
        stubFor(
            post(urlPathEqualTo("/content/my-site/en_gl/home"))
                .withHeader(CSRF_TOKEN, equalTo("admin"))
                .willReturn(aResponse().withStatus(201))
        )
    }
}