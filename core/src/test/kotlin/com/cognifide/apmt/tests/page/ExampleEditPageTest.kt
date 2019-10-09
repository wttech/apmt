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
                .withHeader("apmt-header1", equalTo("apmt-value1"))
                .withHeader("apmt-header2", equalTo("apmt-value2"))
                .willReturn(aResponse().withStatus(200))
        )
        stubFor(
            post(urlPathEqualTo("/content/my-site/en_gl/home/jcr%3Acontent"))
                .withHeader("apmt-header1", equalTo("apmt-value1"))
                .withHeader("apmt-header2", equalTo("apmt-value2"))
                .willReturn(aResponse().withStatus(200))
        )
    }
}