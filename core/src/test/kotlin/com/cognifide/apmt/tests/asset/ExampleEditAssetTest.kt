package com.cognifide.apmt.tests.asset

import com.cognifide.apmt.actions.CSRF_TOKEN
import com.cognifide.apmt.tests.ApmtTestCases
import com.cognifide.apmt.tests.ApmtUsers
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUser
import com.cognifide.apmt.util.AemStubExtension.Companion.registerUsers
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.BeforeEach

@AemStub
class ExampleEditAssetTest : EditAssetTest(
    ApmtTestCases.EDIT_ASSET
) {

    @BeforeEach
    fun beforeEach() {
        registerUser("admin", "admin")
        registerUsers(*ApmtUsers.values())

        stubFor(
            post(urlPathMatching("/content/dam/my-product/(images|screens)"))
                .willReturn(aResponse().withStatus(500))
        )
        stubFor(
            post(urlPathMatching("/content/dam/my-product/(images|screens)"))
                .withHeader(CSRF_TOKEN, equalTo(ApmtUsers.SUPER_AUTHOR.username))
                .willReturn(aResponse().withStatus(200))
        )
    }
}