package com.cognifide.apmt.tests.asset

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.asset.CreateAsset
import com.cognifide.apmt.actions.asset.EditAsset
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to edit asset")
abstract class EditAssetTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private val authorInstance = ConfigurationProvider.authorInstance

    @DisplayName("User can edit assets")
    @ParameterizedTest
    @Allowed
    fun userCanEditAssets(user: User, path: String) {
        val createAsset = CreateAsset(authorInstance, ConfigurationProvider.adminUser, path)
        createAsset.execute()
        addUndoAction(createAsset)

        EditAsset(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @DisplayName("User cannot edit assets")
    @ParameterizedTest
    @Denied
    fun userCannotEditAssets(user: User, path: String) {
        val createAsset = CreateAsset(authorInstance, ConfigurationProvider.adminUser, path)
        createAsset.execute()
        addUndoAction(createAsset)

        EditAsset(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(500)
    }
}
