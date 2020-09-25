package com.cognifide.apmt.tests.page

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.page.CreatePage
import com.cognifide.apmt.actions.page.EditPageProperties
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to edit page properties")
abstract class EditPagePropertiesTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private var authorInstance = ConfigurationProvider.authorInstance

    @DisplayName("User can edit page properties")
    @ParameterizedTest
    @Allowed
    fun userCanEditPageProperties(user: User, path: String) {
        val createPage = CreatePage(authorInstance, ConfigurationProvider.apmtUser, path)
        createPage.execute()
        addUndoAction(createPage)

        EditPageProperties(authorInstance, user, "$path/jcr:content")
            .execute()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @DisplayName("User cannot edit page properties")
    @ParameterizedTest
    @Denied
    fun userCannotEditPageProperties(user: User, path: String) {
        val createPage = CreatePage(authorInstance, ConfigurationProvider.apmtUser, path)
        createPage.execute()
        addUndoAction(createPage)

        EditPageProperties(authorInstance, user, "$path/jcr:content")
            .execute()
            .then()
            .assertThat()
            .statusCode(500)
    }
}
