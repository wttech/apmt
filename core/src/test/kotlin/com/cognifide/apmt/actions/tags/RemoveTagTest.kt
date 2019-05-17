package com.cognifide.apmt.actions.tags

import com.cognifide.apmt.MOCK_SERVER
import com.cognifide.apmt.TEST_ADMIN
import com.cognifide.apmt.TEST_USER
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.HumanReadableCamelCase
import com.github.tomakehurst.wiremock.client.BasicCredentials
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.Test

const val TAG_PATH = "/removed-tag"

@DisplayNameGeneration(HumanReadableCamelCase::class)
class RemoveTagTest {
    private val removeTag = RemoveTag(MOCK_SERVER, TEST_USER, TAG_PATH)

    @Test
    fun onSuccessReturns204() {
        assertEquals(204, removeTag.successCode())
    }

    @Test
    fun onFailureReturns500() {
        assertEquals(500, removeTag.failureCode())
    }

    @Test
    fun callingUndoDoesNothing() {
        assertNull(removeTag.undo())
    }

    @Test
    @AemStub
    fun callingExecutePerformsDelete() {
        stubFor(
            delete(urlPathEqualTo(TAG_PATH))
                .willReturn(ok())
        )

        removeTag.execute()

        verify(deleteRequestedFor(urlPathEqualTo(TAG_PATH)))
    }

    @Test
    @AemStub
    fun callingPrepareCreatesTagWithAdminSession() {
        stubFor(
            post(urlPathEqualTo(TAG_PATH))
                .willReturn(ok())
        )

        removeTag.prepare()

        verify(
            postRequestedFor(urlPathEqualTo(TAG_PATH))
                .withBasicAuth(BasicCredentials(TEST_ADMIN.username, TEST_ADMIN.password))
        )
    }
}
