package com.cognifide.apmt.tests.page

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.page.CreatePage
import com.cognifide.apmt.actions.page.OpenPage
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.config.Instance
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to open pages")
abstract class OpenPageTest(
    vararg testCases: TestCase,
    private val instance: Instance = ConfigurationProvider.authorInstance,
    private val queryParams: Map<String, String> = mapOf()
) : ApmtBaseTest(*testCases) {

    @DisplayName("User can open pages")
    @ParameterizedTest
    @Allowed
    fun userCanOpenPages(user: User, path: String) {
        if (instance == ConfigurationProvider.authorInstance) {
            val createPage = CreatePage(instance, ConfigurationProvider.apmtUser, path)
            createPage.execute()
            addUndoAction(createPage)
        }

        OpenPage(instance, user, path, queryParams)
            .execute()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @DisplayName("User cannot open pages")
    @ParameterizedTest
    @Denied
    fun userCannotOpenPages(user: User, path: String) {
        if (instance == ConfigurationProvider.authorInstance) {
            val createPage = CreatePage(instance, ConfigurationProvider.apmtUser, path)
            createPage.execute()
            addUndoAction(createPage)
        }

        OpenPage(instance, user, path, queryParams)
            .execute()
            .then()
            .assertThat()
            .statusCode(404)
    }
}
