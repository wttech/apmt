package com.cognifide.apmt.tests.resource

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.resource.CreateResource
import com.cognifide.apmt.actions.resource.EditResource
import com.cognifide.apmt.common.Resource
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to edit resources")
abstract class EditResourceTest(
    vararg testCases: TestCase,
    private val createdResource: (Resource.() -> Unit),
    private val editedResource: (Resource.() -> Unit)
) : ApmtBaseTest(*testCases) {

    private val authorInstance = ConfigurationProvider.authorInstance

    @DisplayName("User can edit resources")
    @ParameterizedTest
    @Allowed
    fun userCanEditResources(user: User, path: String) {
        val createResource = CreateResource(authorInstance, ConfigurationProvider.apmtUser, path, createdResource)
        addUndoAction(createResource)
        createResource.execute()

        EditResource(authorInstance, user, path, editedResource)
            .execute()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @DisplayName("User cannot edit resources")
    @ParameterizedTest
    @Denied
    fun userCannotEditResources(user: User, path: String) {
        val createResource = CreateResource(authorInstance, ConfigurationProvider.apmtUser, path, createdResource)
        addUndoAction(createResource)
        createResource.execute()

        EditResource(authorInstance, user, path, editedResource)
            .execute()
            .then()
            .assertThat()
            .statusCode(500)
    }
}

