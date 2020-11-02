package com.cognifide.apmt.tests.page

import com.cognifide.apmt.BasicTestCase
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.ApmtTestCases
import com.cognifide.apmt.tests.ApmtUsers
import com.cognifide.apmt.tests.testCase
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUser
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUsers
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

@AemStub
class ApmtOpenPageOnPublishTest : OpenPageTest(
    ApmtTestCases.OPEN_PAGE,
    instance = ConfigurationProvider.publishInstance
) {

    @BeforeEach
    fun beforeEach() {
        registerUser("admin", "admin")
        registerUsers(*ApmtUsers.values())

        stubFor(
            get(urlPathEqualTo("/content/my-site/en_gl/home"))
                .withHeader("CSRF-Token", matching("author|super-author"))
                .willReturn(aResponse().withStatus(200))
        )

        stubFor(
            get(urlPathEqualTo("/content/my-site/en_gl/home"))
                .withHeader("CSRF-Token", equalTo("user"))
                .willReturn(aResponse().withStatus(404))
        )
    }

    @AfterEach
    fun verifyIfPageWasNotCreated() {
        verify(0, postRequestedFor(urlPathEqualTo("/content/my-site/en_gl/home")))
    }
}

@AemStub
class ApmtOpenPageOnAuthorTest : OpenPageTest(
    testCase {
        paths(
            "/content/my-site/en_gl/home.html" // Mind the `.html` in test on author
        )
        allowedUsers (
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    },
    instance = ConfigurationProvider.authorInstance
) {
    @BeforeEach
    fun beforeEach() {
        registerUser("admin", "admin")
        registerUsers(*ApmtUsers.values())

        stubFor(
            get(urlPathEqualTo("/content/my-site/en_gl/home.html"))
                .withHeader("CSRF-Token", matching("author|super-author"))
                .willReturn(aResponse().withStatus(200))
        )

        stubFor(
            get(urlPathEqualTo("/content/my-site/en_gl/home.html"))
                .withHeader("CSRF-Token", equalTo("user"))
                .willReturn(aResponse().withStatus(404))
        )
    }
}