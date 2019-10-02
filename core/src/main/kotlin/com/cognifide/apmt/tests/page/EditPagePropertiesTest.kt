package com.cognifide.apmt.tests.page

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.page.CreatePage
import com.cognifide.apmt.actions.page.EditPageProperties
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to edit page properties")
abstract class EditPagePropertiesTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private var authorInstance = ConfigurationProvider.authorInstance
    private var undoableAction: Action? = null

    @DisplayName("User can edit page properties")
    @Allowed
    fun userCanEditPageProperties(user: User, path: String) {
        undoableAction = CreatePage(authorInstance, ConfigurationProvider.adminUser, path)
        undoableAction?.execute()

        EditPageProperties(authorInstance, user, "$path/jcr:content")
            .execute()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @DisplayName("User cannot edit page properties")
    @Denied
    fun userCannotEditPageProperties(user: User, path: String) {
        undoableAction = CreatePage(authorInstance, ConfigurationProvider.adminUser, path)
        undoableAction?.execute()

        EditPageProperties(authorInstance, user, "$path/jcr:content")
            .execute()
            .then()
            .assertThat()
            .statusCode(500)
    }

    @AfterEach
    fun cleanup() {
        undoableAction?.undo()
    }
}
