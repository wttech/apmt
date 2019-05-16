package com.cognifide.apmt.actions.tags

import com.cognifide.apmt.MOCK_SERVER
import com.cognifide.apmt.TEST_USER
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.HumanReadableCamelCase
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.Test

@DisplayName("CreateTag action:")
@DisplayNameGeneration(HumanReadableCamelCase::class)
class CreateTagTest {

    private val tagPath = "/created-tag"

    private val createTag = CreateTag(MOCK_SERVER, TEST_USER, tagPath)

    @Test
    fun callingPrepareDoesNothing() {
        assertNull(createTag.prepare())
    }

    @AemStub
    @Test
    fun callingExecutePerformsPost() {
        stubFor(
            post(urlPathEqualTo(tagPath))
                .willReturn(ok())
        )

        createTag.execute()

        verify(
            postRequestedFor(urlPathEqualTo(tagPath))
                .withRequestBody(equalTo("jcr%3AprimaryType=cq%3ATag&jcr%3Atitle=%5BAPMT%5D%20Test%20Tag&sling%3AresourceType=cq%2Ftagging%2Fcomponents%2Ftag"))
        )
    }

    @Test
    @AemStub
    fun callingUndoPerformsDelete() {
        stubFor(delete(urlPathEqualTo(tagPath)).willReturn(ok()))

        createTag.undo()

        verify(deleteRequestedFor(urlPathEqualTo(tagPath)))
    }

    @Test
    fun onSuccessReturns201() {
        assertEquals(201, createTag.successCode())
    }

    @Test
    fun onFailureReturns500() {
        assertEquals(500, createTag.failureCode())
    }
}
