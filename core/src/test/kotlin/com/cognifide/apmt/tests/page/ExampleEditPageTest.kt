package com.cognifide.apmt.tests.page

import com.cognifide.apmt.tests.ExampleTestCases
import com.cognifide.apmt.tests.ExampleUsers
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUser
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUsers
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeEach

@AemStub
class ExampleEditPageTest : EditPageTest(
    ExampleTestCases.EDIT_PAGE
) {

    @BeforeEach
    fun beforeEach() {
        registerUser("admin", "admin")
        registerUsers(*ExampleUsers.values())

        stubFor(
            post(urlPathEqualTo("/content/my-site/en_gl/home"))
                .willReturn(aResponse().withStatus(200))
        )
        stubFor(
            post(urlPathEqualTo("/content/my-site/en_gl/home/jcr%3Acontent"))
                .willReturn(aResponse().withStatus(200))
        )
    }
}