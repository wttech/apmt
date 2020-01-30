package com.cognifide.apmt.tests.page

import com.cognifide.apmt.BasicTestCase
import com.cognifide.apmt.actions.CSRF_TOKEN
import com.cognifide.apmt.tests.*
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUser
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUsers
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeEach

@AemStub
class ApmtCreatePageTest : CreatePageTest(
    ApmtTestCases.ADD_PAGE,
    testCase {
        paths(
            "/content/my-site/de_de/home"
        )
        allowedUsers(
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    },
    BasicTestCase {
        paths(
            "/content/my-site/fr_fr/home"
        )
        allowedUsers(
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    },
    pageContent = {
        jcrTitle = "[APMT] New Test Page"
        slingResourceType = APMT_PAGE
        cqTemplate = APMT_PAGE_TEMPLATE

        "text" {
            jcrPrimaryType = "nt:unstructured"
            slingResourceType = APMT_TEXT
            "value" set "Hello World!"
        }
    }
) {

    @BeforeEach
    fun beforeEach() {
        registerUser("admin", "admin")
        registerUsers(*ApmtUsers.values())

        stubFor(
            post(urlPathMatching("/content/my-site/en_gl/home"))
                .willReturn(aResponse().withStatus(201))
        )
        stubFor(
            post(urlPathMatching("/content/my-site/en_gl/home"))
                .withHeader(CSRF_TOKEN, equalTo(ApmtUsers.USER.username))
                .willReturn(aResponse().withStatus(500))
        )
    }
}

