package com.cognifide.apmt.tests.resource

import com.cognifide.apmt.actions.CSRF_TOKEN
import com.cognifide.apmt.tests.APMT_TEXT
import com.cognifide.apmt.tests.ApmtUsers
import com.cognifide.apmt.tests.testCase
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUser
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUsers
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeEach

@AemStub
class ApmtEditResourceTest : EditResourceTest(
    testCase {
        paths(
            "/content/my-site/de_de/home/text"
        )
        allowedUsers(
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    },
    createdResource = {
        jcrPrimaryType = "nt:unstructured"
        slingResourceType = APMT_TEXT
        "value" set "Hello World!"
    },
    editedResource = {
        jcrPrimaryType = "nt:unstructured"
        slingResourceType = APMT_TEXT
        "value" set "Hello edited World!"
    }
) {

    @BeforeEach
    fun beforeEach() {
        registerUser("admin", "admin")
        registerUsers(*ApmtUsers.values())

        // apmt user creates resource first
        stubFor(
            post(urlPathEqualTo("/content/my-site/de_de/home/text"))
                .withHeader(CSRF_TOKEN, equalTo("admin"))
                .willReturn(aResponse().withStatus(201))
        )

        stubFor(
            post(urlPathEqualTo("/content/my-site/de_de/home/text"))
                .withHeader(CSRF_TOKEN, matching("author|super-author"))
                .willReturn(aResponse().withStatus(200))
        )

        stubFor(
            post(urlPathEqualTo("/content/my-site/de_de/home/text"))
                .withHeader(CSRF_TOKEN, equalTo("user"))
                .willReturn(aResponse().withStatus(500))
        )
    }
}