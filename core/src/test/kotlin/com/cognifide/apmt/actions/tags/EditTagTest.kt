package com.cognifide.apmt.actions.tags

import com.cognifide.apmt.MOCK_SERVER
import com.cognifide.apmt.TEST_ADMIN
import com.cognifide.apmt.TEST_USER
import com.cognifide.apmt.util.AemStub
import com.cognifide.apmt.util.HumanReadableCamelCase
import com.github.tomakehurst.wiremock.client.BasicCredentials
import com.github.tomakehurst.wiremock.client.WireMock.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.DisplayNameGeneration
import org.junit.jupiter.api.Test

@DisplayName("EditTag action:")
@DisplayNameGeneration(HumanReadableCamelCase::class)
class EditTagTest {

    private val tagPath = "/edited-tag"

    private val editTag = EditTag(MOCK_SERVER, TEST_USER, tagPath)

    @Test
    @AemStub
    fun callingPrepareCreatesTagWithAdminSession() {
        stubFor(
            post(urlPathEqualTo(tagPath))
                .willReturn(ok())
        )

        editTag.prepare()

        verify(
            postRequestedFor(urlPathEqualTo(tagPath))
                .withRequestBody(equalTo("jcr%3AprimaryType=cq%3ATag&jcr%3Atitle=%5BAPMT%5D%20Test%20Tag&sling%3AresourceType=cq%2Ftagging%2Fcomponents%2Ftag"))
                .withBasicAuth(BasicCredentials(TEST_ADMIN.username, TEST_ADMIN.password))
        )
    }

    @AemStub
    @Test
    fun callingExecutePerformsPost() {
        stubFor(
            post(urlPathEqualTo(tagPath))
                .willReturn(ok())
        )

        editTag.execute()

        verify(
            postRequestedFor(urlPathEqualTo(tagPath))
                .withRequestBody(equalTo("jcr%3Atitle=%5BAPMT%5D%20Changed%20title"))
        )
    }

    @Test
    @AemStub
    fun callingUndoRemovesPreparedTagWithAdminSession() {
        stubFor(
            delete(urlPathEqualTo(tagPath))
                .willReturn(ok())
        )

        editTag.undo()

        verify(
            deleteRequestedFor(urlPathEqualTo(tagPath))
                .withBasicAuth(BasicCredentials(TEST_ADMIN.username, TEST_ADMIN.password))
        )
    }

    @Test
    fun onSuccessReturns200() {
        assertEquals(200, editTag.successCode())
    }

    @Test
    fun onFailureReturns500() {
        assertEquals(500, editTag.failureCode())
    }
}
