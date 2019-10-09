package com.cognifide.apmt.tests.asset

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.asset.CreateAsset
import com.cognifide.apmt.actions.asset.RemoveAsset
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
@DisplayName("Check user permissions to remove asset")
abstract class RemoveAssetTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private val authorInstance = ConfigurationProvider.authorInstance
    private var undoAction: Action? = null

    @DisplayName("User can remove asset")
    @ParameterizedTest
    @Allowed
    fun userCanDeleteAssets(user: User, path: String) {
        undoAction = CreateAsset(authorInstance, ConfigurationProvider.adminUser, path)
        undoAction?.execute()

        RemoveAsset(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(204)
    }

    @DisplayName("User can not remove asset")
    @ParameterizedTest
    @Denied
    fun userCannotDeleteAssets(user: User, path: String) {
        undoAction = CreateAsset(authorInstance, ConfigurationProvider.adminUser, path)
        undoAction?.execute()

        RemoveAsset(authorInstance, user, path)
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
