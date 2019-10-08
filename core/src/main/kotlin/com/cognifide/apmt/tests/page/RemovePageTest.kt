package com.cognifide.apmt.tests.page

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.page.CreatePage
import com.cognifide.apmt.actions.page.RemovePage
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
@DisplayName("Check user permissions to remove pages")
abstract class RemovePageTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private var authorInstance = ConfigurationProvider.authorInstance
    private var undoAction: Action? = null

    @DisplayName("User can remove pages")
    @ParameterizedTest
    @Allowed
    fun userCanRemovePages(user: User, path: String) {
        undoAction = CreatePage(authorInstance, ConfigurationProvider.adminUser, path)
        undoAction?.execute()

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
        undoAction = CreatePage(authorInstance, ConfigurationProvider.adminUser, path)
        undoAction?.execute()

        RemovePage(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(403)
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
