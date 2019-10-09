package com.cognifide.apmt.tests.resource

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.resource.CreateResource
import com.cognifide.apmt.common.Resource
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to create resources")
abstract class CreateResourceTest(
    vararg testCases: TestCase,
    private val resource: (Resource.() -> Unit)? = null
) : ApmtBaseTest(*testCases) {

    private val authorInstance = ConfigurationProvider.authorInstance
    private var undoAction: Action? = null

    @DisplayName("User can create resources")
    @ParameterizedTest
    @Allowed
    fun userCanCreateResources(user: User, path: String) {
        undoAction = CreateResource(authorInstance, user, path, resource)
        undoAction
            ?.execute()
            ?.then()
            ?.assertThat()
            ?.statusCode(201)
    }

    @DisplayName("User cannot create resources")
    @ParameterizedTest
    @Denied
    fun userCannotCreateResources(user: User, path: String) {
        undoAction = CreateResource(authorInstance, user, path, resource)
        undoAction
            ?.execute()
            ?.then()
            ?.assertThat()
            ?.statusCode(500)
    }

    @AfterEach
    fun cleanUp() {
        undoAction?.undo()
    }
}

