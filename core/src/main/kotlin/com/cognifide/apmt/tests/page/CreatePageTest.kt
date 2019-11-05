package com.cognifide.apmt.tests.page

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.page.CreatePage
import com.cognifide.apmt.common.PageContent
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to create pages")
abstract class CreatePageTest(
    vararg testCases: TestCase,
    private val pageContent: (PageContent.() -> Unit) = {
        jcrTitle = "[APMT] New Test Page"
    }
) : ApmtBaseTest(*testCases) {

    private val authorInstance = ConfigurationProvider.authorInstance

    @DisplayName("User can create pages")
    @ParameterizedTest
    @Allowed
    fun userCanCreatePages(user: User, path: String) {
        addUndoAction(CreatePage(authorInstance, ConfigurationProvider.adminUser, path))

        CreatePage(authorInstance, user, path, pageContent)
            .execute()
            .then()
            .assertThat()
            .statusCode(201)
    }

    @DisplayName("User cannot create pages")
    @ParameterizedTest
    @Denied
    fun userCannotCreatePages(user: User, path: String) {
        addUndoAction(CreatePage(authorInstance, ConfigurationProvider.adminUser, path))

        CreatePage(authorInstance, user, path, pageContent)
            .execute()
            .then()
            .assertThat()
            .statusCode(500)
    }
}

