package com.cognifide.apmt.tests.page

import com.cognifide.apmt.actions.CSRF_TOKEN
import com.cognifide.apmt.tests.ExampleTestCases
import com.cognifide.apmt.tests.ExampleUsers
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUser
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUsers
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeEach

@AemStub
class ExampleCreatePageTest : CreatePageTest(
    ExampleTestCases.ADD_PAGE,
    pageContent = {
        jcrTitle = "Example Page"
        slingResourceType = "apmt/components/testPage"
        cqTemplate = "apmt/templates/testPage"

        properties("apmtType" to "apmtTestPage")
    }
) {

    @BeforeEach
    fun beforeEach() {
        registerUser("admin", "admin")
        registerUsers(*ExampleUsers.values())

        stubFor(
            post(urlPathMatching("/content/my-site/en_gl/home"))
                .willReturn(aResponse().withStatus(201))
        )
        stubFor(
            post(urlPathMatching("/content/my-site/en_gl/home"))
                .withHeader(CSRF_TOKEN, equalTo(ExampleUsers.USER.username))
                .willReturn(aResponse().withStatus(500))
        )
    }
}

