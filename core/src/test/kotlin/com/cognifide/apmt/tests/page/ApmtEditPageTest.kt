package com.cognifide.apmt.tests.page

import com.cognifide.apmt.tests.*
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUser
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUsers
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeEach

@AemStub
class ApmtEditPageTest : EditPageTest(
    ApmtTestCases.EDIT_PAGE,
    createdPageContent = {
        jcrTitle = "[APMT] New Test Page"
        slingResourceType = APMT_PAGE
        cqTemplate = APMT_PAGE_TEMPLATE

        "text" {
            jcrPrimaryType = "nt:unstructured"
            slingResourceType = APMT_TEXT
            "value" set "Hello World!"
        }
    },
    editedPageContent = {
        jcrTitle = "[APMT] Edited Test Page"
        "text" {
            "value" set "Good bye!"
        }
    }
) {

    @BeforeEach
    fun beforeEach() {
        registerUser("admin", "admin")
        registerUsers(*ApmtUsers.values())

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