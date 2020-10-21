package com.cognifide.apmt.tests.resource

import com.cognifide.apmt.actions.CSRF_TOKEN
import com.cognifide.apmt.tests.ApmtUsers
import com.cognifide.apmt.tests.testCase
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.AemStubExtension
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeEach

@AemStub
class ApmtReadResourceTest : ReadResourceTest(
    testCase {
        paths(
            "/libs/dam/gui/content/assets/annotate/jcrcontent/actions/quickpublish"
        )
        allowedUsers(
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    }
) {

    @BeforeEach
    fun beforeEach() {
        AemStubExtension.registerUser("admin", "admin")
        AemStubExtension.registerUsers(*ApmtUsers.values())

        stubFor(
            get(urlPathEqualTo("/libs/dam/gui/content/assets/annotate/jcrcontent/actions/quickpublish.json"))
                .withHeader(CSRF_TOKEN, matching("author|super-author"))
                .willReturn(aResponse().withStatus(200))
        )

        stubFor(
            get(urlPathEqualTo("/libs/dam/gui/content/assets/annotate/jcrcontent/actions/quickpublish.json"))
                .withHeader(CSRF_TOKEN, equalTo("user"))
                .willReturn(aResponse().withStatus(403))
        )
    }
}

@AemStub
class ApmtReadResourceWithCustomDeniedStatusTest : ReadResourceTest(
    testCase {
        paths(
            "/libs/dam/gui/content/assets/annotate/jcrcontent/actions/quickpublish"
        )
        allowedUsers(
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    },
    deniedStatusCode = 404
) {

    @BeforeEach
    fun beforeEach() {
        AemStubExtension.registerUser("admin", "admin")
        AemStubExtension.registerUsers(*ApmtUsers.values())

        stubFor(
            get(urlPathEqualTo("/libs/dam/gui/content/assets/annotate/jcrcontent/actions/quickpublish.json"))
                .withHeader(CSRF_TOKEN, matching("author|super-author"))
                .willReturn(aResponse().withStatus(200))
        )

        stubFor(
            get(urlPathEqualTo("/libs/dam/gui/content/assets/annotate/jcrcontent/actions/quickpublish.json"))
                .withHeader(CSRF_TOKEN, equalTo("user"))
                .willReturn(aResponse().withStatus(404))
        )
    }
}