package com.cognifide.apmt

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.TestInfo

abstract class PermissionTest(
    private val configuration: PermissionTestsConfiguration.() -> Unit
) {
    @TestFactory
    fun runTests() = createPermissionTests(configuration)

    @BeforeEach
    fun beforeEach(testInfo: TestInfo) {
        testInfo.displayName
    }
}
