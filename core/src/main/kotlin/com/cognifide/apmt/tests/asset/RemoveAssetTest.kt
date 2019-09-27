package com.cognifide.apmt.tests.asset

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.asset.CreateAsset
import com.cognifide.apmt.actions.asset.RemoveAsset
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.ApmtBaseTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to remove asset")
abstract class RemoveAssetTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private val authorInstance = ConfigurationProvider.authorInstance
    private var undoableAction: Action? = null

    @DisplayName("User can remove asset")
    @ParameterizedTest(name = "{index} => User: {0} Path: {1}")
    @MethodSource(ALLOWED)
    fun userCanDeleteAssets(user: User, path: String) {
        undoableAction = CreateAsset(authorInstance, ConfigurationProvider.adminUser, path)
        undoableAction?.execute()

        RemoveAsset(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(204)
    }

    @DisplayName("User can not remove asset")
    @ParameterizedTest(name = "{index} => User: {0} Path: {1}")
    @MethodSource(DENIED)
    fun userCannotDeleteAssets(user: User, path: String) {
        undoableAction = CreateAsset(authorInstance, ConfigurationProvider.adminUser, path)
        undoableAction?.execute()

        RemoveAsset(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(403)
    }

    @AfterEach
    fun cleanup() {
        undoableAction?.undo()
    }
}
