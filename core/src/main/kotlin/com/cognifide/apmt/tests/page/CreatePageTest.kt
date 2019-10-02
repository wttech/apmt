package com.cognifide.apmt.tests.page

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.page.CreatePage
import com.cognifide.apmt.common.PageContent
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to create pages")
abstract class CreatePageTest(
    vararg testCases: TestCase,
    private var pageContent: (PageContent.() -> Unit)? = null
) : ApmtBaseTest(*testCases) {

    private val authorInstance = ConfigurationProvider.authorInstance
    private var undoableAction: Action? = null

    @DisplayName("User can create pages")
    @ParameterizedTest
    @Allowed
    fun userCanCreatePages(user: User, path: String) {
        undoableAction = CreatePage(authorInstance, ConfigurationProvider.adminUser, path)
        undoableAction?.undo()

        CreatePage(authorInstance, user, path, toPageProperties(pageContent))
            .execute()
            .then()
            .assertThat()
            .statusCode(201)
    }

    @DisplayName("User cannot create pages")
    @ParameterizedTest
    @Denied
    fun userCannotCreatePages(user: User, path: String) {
        undoableAction = CreatePage(authorInstance, ConfigurationProvider.adminUser, path)
        undoableAction?.undo()

        CreatePage(authorInstance, user, path, toPageProperties(pageContent))
            .execute()
            .then()
            .assertThat()
            .statusCode(500)
    }

    private fun toPageProperties(function: (PageContent.() -> Unit)?): Map<String, String> {
        val content = PageContent()
        if (function != null) {
            content.apply(function)
        }
        return content.toPageProperties()
    }

    @AfterEach
    fun cleanup() {
        undoableAction?.undo()
    }
}

