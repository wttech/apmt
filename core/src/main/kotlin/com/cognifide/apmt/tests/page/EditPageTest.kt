package com.cognifide.apmt.tests.page

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.page.CreatePage
import com.cognifide.apmt.actions.page.EditPage
import com.cognifide.apmt.common.PageContent
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to edit pages")
abstract class EditPageTest(
    vararg testCases: TestCase,
    private val createdPageContent: (PageContent.() -> Unit) = {
        jcrTitle = "[APMT] New Test Page"
    },
    private val editedPageContent: (PageContent.() -> Unit) = {
        jcrTitle = "[APMT] Edited Test Page"
    }
) : ApmtBaseTest(*testCases) {

    private var authorInstance = ConfigurationProvider.authorInstance

    @DisplayName("User can edit pages")
    @ParameterizedTest
    @Allowed
    fun userCanEditPages(user: User, path: String) {
        val createPage = CreatePage(authorInstance, ConfigurationProvider.adminUser, path, createdPageContent)
        createPage.execute()
        addUndoAction(createPage)

        EditPage(authorInstance, user, path, editedPageContent)
            .execute()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @DisplayName("User cannot edit pages")
    @ParameterizedTest
    @Denied
    fun userCannotEditPages(user: User, path: String) {
        val createPage = CreatePage(authorInstance, ConfigurationProvider.adminUser, path, createdPageContent)
        createPage.execute()
        addUndoAction(createPage)

        EditPage(authorInstance, user, path, editedPageContent)
            .execute()
            .then()
            .assertThat()
            .statusCode(500)
    }
}
