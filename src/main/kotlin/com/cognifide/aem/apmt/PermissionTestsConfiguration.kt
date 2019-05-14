package com.cognifide.aem.apmt

class PermissionTestsConfiguration(
    internal val users: MutableList<User> = mutableListOf(),
    internal val groups: MutableList<Group> = mutableListOf(),
    internal val testCaseConfigurations: MutableList<TestCaseConfiguration> = mutableListOf()
) {

    fun registerUsers(users: Array<out User>) = this.users.addAll(users)
    fun registerUsers(users: List<out User>) = this.users.addAll(users)

    fun registerGroups(groups: Array<out Group>) = this.groups.addAll(groups)
    fun registerGroups(groups: List<out Group>) = this.groups.addAll(groups)

    operator fun String.invoke(initConfiguration: TestCaseConfiguration.() -> Unit) {
        val configuration = TestCaseConfiguration().apply(initConfiguration)
        configuration.name = this
        testCaseConfigurations.add(configuration)
    }
}