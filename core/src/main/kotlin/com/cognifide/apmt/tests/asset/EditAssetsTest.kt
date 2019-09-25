package com.cognifide.apmt.tests.asset

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.asset.AssetCreation
import com.cognifide.apmt.actions.asset.AssetEdition
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.ApmtBaseTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to edit asset")
abstract class EditAssetsTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private val authorInstance = ConfigurationProvider.authorInstance
    private var undoAction: Action? = null

    @DisplayName("User can edit asset under path on instance")
    @ParameterizedTest(name = "{index} => User: {0} Path: {1}")
    @MethodSource(ALLOWED)
    fun userCanEditAssets(user: User, path: String) {
        undoAction = AssetCreation(authorInstance, ConfigurationProvider.adminUser, path)
        undoAction?.prepare()

        AssetEdition(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @AfterEach
    fun cleanup() {
        undoAction!!.undo()
    }
}
