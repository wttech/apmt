package com.cognifide.apmt.tests.page

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.page.CreatePage
import com.cognifide.apmt.actions.page.EditPage
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to edit pages")
abstract class EditPageTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private var authorInstance = ConfigurationProvider.authorInstance
    private var undoAction: Action? = null

    @DisplayName("User can edit pages")
    @ParameterizedTest
    @Allowed
    fun userCanEditPages(user: User, path: String) {
        undoAction = CreatePage(authorInstance, ConfigurationProvider.adminUser, path)
        undoAction?.execute()

        EditPage(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @DisplayName("User cannot edit pages")
    @ParameterizedTest
    @Denied
    fun userCannotEditPages(user: User, path: String) {
        undoAction = CreatePage(authorInstance, ConfigurationProvider.adminUser, path)
        undoAction?.execute()

        EditPage(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(500)
    }

    @BeforeEach
    fun init() {
        undoAction = null
    }

    @AfterEach
    fun cleanup() {
        undoAction?.undo()
    }
}
