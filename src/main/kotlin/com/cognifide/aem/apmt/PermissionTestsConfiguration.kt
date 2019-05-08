package com.cognifide.aem.apmt

class PermissionTestsConfiguration(
    internal val testCaseConfigurations: MutableList<TestCaseConfiguration> = mutableListOf()
) {

    operator fun String.invoke(initConfiguration: TestCaseConfiguration.() -> Unit) {
        val configuration = TestCaseConfiguration().apply(initConfiguration)
        configuration.name = this
        testCaseConfigurations.add(configuration)
    }
}