package com.cognifide.apmt.actions.common

import com.cognifide.apmt.MOCK_SERVER
import com.cognifide.apmt.TEST_USER
import com.cognifide.apmt.junit.AemStub
import com.cognifide.apmt.junit.HumanReadableCamelCase
import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.Test

@DisplayName("ReadResource action:")
@DisplayNameGeneration(HumanReadableCamelCase::class)
class ReadResourceTest {

    private val readResource = ReadResource(MOCK_SERVER, TEST_USER, "/readResource")

    @Test
    fun callingPrepareDoesNothing() {
        assertNull(readResource.prepare())
    }

    @Test
    @AemStub
    fun callingExecutePerformsGet(server: WireMockServer) {
        stubFor(
            get(urlPathEqualTo("/readResource"))
                .willReturn(
                    ok()
                )
        )

        readResource.execute()

        verify(getRequestedFor(urlPathEqualTo("/readResource.json")))
    }

    @Test
    fun callingUndoDoesNothing() {
        assertNull(readResource.undo())
    }

    @Test
    fun onSuccessReturns200() {
        assertEquals(200, readResource.successCode())
    }

    @Test
    fun onFailureReturns404() {
        assertEquals(404, readResource.failureCode())
    }
}
