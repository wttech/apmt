package com.cognifide.apmt.tests.page

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.page.CreatePage
import com.cognifide.apmt.actions.page.RemovePage
import com.cognifide.apmt.common.PageContent
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to remove pages")
abstract class RemovePageTest(
    vararg testCases: TestCase,
    private val pageContent: (PageContent.() -> Unit) = {
        jcrTitle = "[APMT] New Test Page"
    }
) : ApmtBaseTest(*testCases) {

    private var authorInstance = ConfigurationProvider.authorInstance

    @DisplayName("User can remove pages")
    @ParameterizedTest
    @Allowed
    fun userCanRemovePages(user: User, path: String) {
        val createPage = CreatePage(authorInstance, ConfigurationProvider.apmtUser, path, pageContent)
        createPage.execute()
        addUndoAction(createPage)

        RemovePage(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(204)
    }

    @DisplayName("User cannot remove pages")
    @ParameterizedTest
    @Denied
    fun userCannotRemovePages(user: User, path: String) {
        val createPage = CreatePage(authorInstance, ConfigurationProvider.apmtUser, path, pageContent)
        createPage.execute()
        addUndoAction(createPage)

        RemovePage(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(403)
    }
}
