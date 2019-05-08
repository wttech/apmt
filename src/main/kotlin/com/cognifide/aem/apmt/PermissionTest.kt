package com.cognifide.aem.apmt

import org.junit.jupiter.api.TestFactory

abstract class PermissionTest(
    private val configuration: PermissionTestsConfiguration.() -> Unit
) {
    @TestFactory
    fun runTests() = createPermissionTests(configuration)
}