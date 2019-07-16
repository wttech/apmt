package com.cognifide.apmt

class PermissionTestsConfiguration(
    internal val users: MutableList<User> = mutableListOf(),
    internal val testCaseConfigurations: MutableList<TestCaseConfiguration> = mutableListOf()
) {

    fun registerUsers(users: Array<out User>) = this.users.addAll(users)
    fun registerUsers(users: List<out User>) = this.users.addAll(users)

    operator fun String.invoke(initConfiguration: TestCaseConfiguration.() -> Unit) {
        val configuration = TestCaseConfiguration().apply(initConfiguration)
        configuration.name = this
        testCaseConfigurations.add(configuration)
    }
}
