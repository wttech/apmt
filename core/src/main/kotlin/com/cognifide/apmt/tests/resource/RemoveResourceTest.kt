package com.cognifide.apmt.tests.resource

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.resource.CreateResource
import com.cognifide.apmt.actions.resource.RemoveResource
import com.cognifide.apmt.common.Resource
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to remove resources")
abstract class RemoveResourceTest(
    vararg testCases: TestCase,
    private val resource: (Resource.() -> Unit) = {}
) : ApmtBaseTest(*testCases) {

    private val authorInstance = ConfigurationProvider.authorInstance

    @DisplayName("User can remove resources")
    @ParameterizedTest
    @Allowed
    fun userCanRemoveResources(user: User, path: String) {
        val createResource = CreateResource(authorInstance, user, path, resource)
        createResource.execute()
        addUndoAction(createResource)

        RemoveResource(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @DisplayName("User cannot remove resources")
    @ParameterizedTest
    @Denied
    fun userCannotRemoveResources(user: User, path: String) {
        val createResource = CreateResource(authorInstance, user, path, resource)
        createResource.execute()
        addUndoAction(createResource)

        RemoveResource(authorInstance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(500)
    }
}

