package com.cognifide.apmt.tests.resource

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.resource.ReadResource
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.config.Instance
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to read resources")
abstract class ReadResourceTest(
    vararg testCases: TestCase,
    private val instance: Instance = ConfigurationProvider.authorInstance,
    private val deniedStatusCode: Int = 404
) : ApmtBaseTest(*testCases) {

    @DisplayName("User can read resources")
    @ParameterizedTest
    @Allowed
    fun userCanReadResource(user: User, path: String) {
        ReadResource(instance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @DisplayName("User cannot read resources")
    @ParameterizedTest
    @Denied
    fun userCannotReadResource(user: User, path: String) {
        ReadResource(instance, user, path)
            .execute()
            .then()
            .assertThat()
            .statusCode(deniedStatusCode)
    }
}