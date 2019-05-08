package com.cognifide.aem.apmt

class PermissionTestsConfiguration(
    internal val testCaseConfigurations: MutableList<TestCaseConfiguration> = mutableListOf()
) {

    fun testCase(name: String, initConfiguration: TestCaseConfiguration.() -> Unit) {
        val configuration = TestCaseConfiguration().apply(initConfiguration)
        configuration.name = name
        testCaseConfigurations.add(configuration)
    }
}