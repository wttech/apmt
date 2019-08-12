package com.cognifide.apmt.tests.asset

import com.cognifide.apmt.*
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.asset.AssetCreation
import com.cognifide.apmt.config.ConfigurationProvider
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to create asset")
open class CreateAssetsTest(private val testCaseConfiguration: TestCaseConfiguration) {

    constructor(testCaseConfiguration: TestCaseConfiguration.() -> Unit)
            : this(TestCaseConfiguration().apply(testCaseConfiguration))

    constructor(testCase: TestCase)
            : this(testCase.toTestCaseConfiguration())

    private var undoAction: Action? = null

    private val authorInstance = ConfigurationProvider.authorInstance

    @DisplayName("User can create assets")
    @ParameterizedTest(name = "{index} => User: {0} Path: {1}")
    @MethodSource("sourceUserCanCreateAssets")
    fun userCanCreateAssets(user: User, path: String) {
        undoAction = AssetCreation(authorInstance, ConfigurationProvider.adminUser, path)

        AssetCreation(authorInstance, user, path)
            .execute()
            ?.then()
            ?.assertThat()
            ?.statusCode(201)
    }

    @DisplayName("User cannot create assets")
    @ParameterizedTest(name = "{index} => User: {0} Path: {1}")
    @MethodSource("sourceUserCannotCreateAssets")
    fun userCannotCreateAssets(user: User, path: String) {
        undoAction = AssetCreation(authorInstance, ConfigurationProvider.adminUser, path)

        AssetCreation(authorInstance, user, path)
            .execute()
            ?.then()
            ?.assertThat()
            ?.statusCode(500)
    }

    @AfterEach
    fun cleanUp() {
        undoAction!!.undo()
    }

    fun sourceUserCanCreateAssets() = createArguments(testCaseConfiguration)

    fun sourceUserCannotCreateAssets() = createInvertedArguments(testCaseConfiguration)
}
