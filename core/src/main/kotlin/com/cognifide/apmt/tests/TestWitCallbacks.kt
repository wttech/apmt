package com.cognifide.apmt.tests

import com.cognifide.apmt.PermissionTestsConfiguration
import com.cognifide.apmt.User
import com.cognifide.apmt.createArguments
import com.cognifide.apmt.createInvertedArguments
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
open class TestWitCallbacks(private val permissionTestsConfiguration: PermissionTestsConfiguration.() -> Unit) {

    var index = 0

    @BeforeEach
    fun beforeEach() {
        index++
    }

    @DisplayName("Check if user can publish page")
    @ParameterizedTest(name = "User {1} can publish {0}")
    @MethodSource("sourceUserCanPublishPage")
    fun userCanPublishPage(path: String, user: User) {
        assert(index > 0)
    }

    @DisplayName("Check if user can not publish page")
    @ParameterizedTest(name = "User {1} can not publish {0}")
    @MethodSource("sourceUserCanNotPublishPage")
    fun userCanNotPublishPage(path: String, user: User) {
        assert(index > 0)
    }

    fun sourceUserCanPublishPage() = createArguments(permissionTestsConfiguration)

    fun sourceUserCanNotPublishPage() = createInvertedArguments(permissionTestsConfiguration)
}
