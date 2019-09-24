package com.cognifide.apmt.tests.asset

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.asset.AssetEdition
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.createArguments
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to edit asset")
open class EditAssetsTest(private vararg val testCases: TestCase) {

    private var undoAction: Action? = null

    private val authorInstance = ConfigurationProvider.authorInstance

    @DisplayName("User can edit asset under path on instance")
    @ParameterizedTest(name = "{index} => User: {0} Path: {1}")
    @MethodSource("sourceUserCanEditAssets")
    fun userCanEditAssets(user: User, path: String) {
        undoAction = AssetEdition(authorInstance, ConfigurationProvider.adminUser, path)
        undoAction?.prepare()

        AssetEdition(authorInstance, user, path)
            .execute()
            ?.then()
            ?.assertThat()
            ?.statusCode(200)
    }

    @AfterEach
    fun cleanup() {
        undoAction!!.undo()
    }

    fun sourceUserCanEditAssets() = createArguments(testCases.map { it.toTestCaseConfiguration() })
}
