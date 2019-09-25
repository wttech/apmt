package com.cognifide.apmt.tests.page

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.page.PageCreation
import com.cognifide.apmt.actions.page.PageEdition
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.ApmtBaseTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to edit pages")
abstract class EditPagesTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private var authorInstance = ConfigurationProvider.authorInstance
    private var undoableAction: Action? = null

    @DisplayName("User can edit pages")
    @ParameterizedTest(name = "{index} => User: {0} Path: {1}")
    @MethodSource(ALLOWED)
    fun userCanEditPages(user: User, path: String) {
        undoableAction = PageCreation(authorInstance, ConfigurationProvider.adminUser, path)
        undoableAction?.execute()

        PageEdition(authorInstance, user, "$path/jcr:content")
            .execute()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @DisplayName("User cannot edit pages")
    @ParameterizedTest(name = "{index} => User: {0} Path: {1}")
    @MethodSource(DENIED)
    fun userCannotEditPages(user: User, path: String) {
        undoableAction = PageCreation(authorInstance, ConfigurationProvider.adminUser, path)
        undoableAction?.execute()

        PageEdition(authorInstance, user, "$path/jcr:content")
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
