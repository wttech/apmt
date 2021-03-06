package com.cognifide.apmt.tests.asset

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.asset.CreateAsset
import com.cognifide.apmt.actions.asset.RemoveAsset
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to remove asset")
abstract class RemoveAssetTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private val authorInstance = ConfigurationProvider.authorInstance

    @DisplayName("User can remove asset")
    @ParameterizedTest
    @Allowed
    fun userCanRemoveAssets(user: User, path: String) {
        val createAsset = CreateAsset(authorInstance, ConfigurationProvider.apmtUser, path)
        createAsset.execute()
        addUndoAction(createAsset)

        RemoveAsset(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(204)
    }

    @DisplayName("User can not remove asset")
    @ParameterizedTest
    @Denied
    fun userCannotRemoveAssets(user: User, path: String) {
        val createAsset = CreateAsset(authorInstance, ConfigurationProvider.apmtUser, path)
        createAsset.execute()
        addUndoAction(createAsset)

        RemoveAsset(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(403)
    }
}
