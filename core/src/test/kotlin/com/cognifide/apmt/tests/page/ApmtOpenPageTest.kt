package com.cognifide.apmt.tests.page

import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.ApmtTestCases
import com.cognifide.apmt.tests.ApmtUsers
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUser
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUsers
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeEach

@AemStub
class ApmtOpenPageTest : OpenPageTest(
    ApmtTestCases.OPEN_PAGE,
    instance = ConfigurationProvider.publishInstance
) {

    @BeforeEach
    fun beforeEach() {
        registerUser("admin", "admin")
        registerUsers(*ApmtUsers.values())

        stubFor(
            get(urlPathEqualTo("/content/my-site/en_gl/home"))
                .withHeader("apmt-header1", equalTo("apmt-value1"))
                .withHeader("apmt-header2", equalTo("apmt-value2"))
                .willReturn(aResponse().withStatus(200))
        )
    }
}