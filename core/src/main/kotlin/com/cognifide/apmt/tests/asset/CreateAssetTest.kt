package com.cognifide.apmt.tests.asset

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.asset.CreateAsset
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to create asset")
abstract class CreateAssetTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private val authorInstance = ConfigurationProvider.authorInstance
    private var undoAction: Action? = null

    @DisplayName("User can create assets")
    @ParameterizedTest
    @Allowed
    fun userCanCreateAssets(user: User, path: String) {
        undoAction = CreateAsset(authorInstance, ConfigurationProvider.adminUser, path)

        CreateAsset(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(201)
    }

    @DisplayName("User cannot create assets")
    @ParameterizedTest
    @Denied
    fun userCannotCreateAssets(user: User, path: String) {
        undoAction = CreateAsset(authorInstance, ConfigurationProvider.adminUser, path)

        CreateAsset(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(500)
    }

    @AfterEach
    fun cleanUp() {
        undoAction?.undo()
    }
}