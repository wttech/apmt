package com.cognifide.apmt.exampleenum

import com.cognifide.apmt.KotlinUsers
import com.cognifide.apmt.TestCase
import com.cognifide.apmt.TestCaseConfiguration

enum class KotlinTestCases(private val initConfig: TestCaseConfiguration.() -> Unit) : TestCase {

    ADD_ASSETS({
        paths(
            "/content/dam/we-retail-screens",
            "/content/dam/we-retail"
        )
        allowedUsers(
            KotlinUsers.AUTHOR,
            KotlinUsers.SUPER_AUTHOR
        )
    }),
    EDIT_ASSETS({
        paths(
            "/content/dam/we-retail-screens",
            "/content/dam/we-retail"
        )
        allowedUsers(
            KotlinUsers.AUTHOR,
            KotlinUsers.SUPER_AUTHOR
        )
    });

    override fun toTestCaseConfiguration(): TestCaseConfiguration {
        val testCaseConfiguration = TestCaseConfiguration().apply(initConfig)
        testCaseConfiguration.allUsers(KotlinUsers.values())
        return testCaseConfiguration
    }
}