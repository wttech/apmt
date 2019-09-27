package com.cognifide.apmt.tests

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.TestCaseConfiguration

enum class ExampleTestCases(private val initConfig: TestCaseConfiguration.() -> Unit) : TestCase {

    ADD_ASSET({
        paths(
            "/content/dam/my-product/screens",
            "/content/dam/my-product/images"
        )
        allowedUsers(
            ExampleUsers.AUTHOR,
            ExampleUsers.SUPER_AUTHOR
        )
    }),
    EDIT_ASSET({
        paths(
            "/content/dam/my-product/screens",
            "/content/dam/my-product/images"
        )
        deniedUsers(
            ExampleUsers.USER,
            ExampleUsers.AUTHOR
        )
    }),
    REMOVE_ASSET({
        paths(
            "/content/dam/my-product/screens",
            "/content/dam/my-product/images"
        )
        deniedUsers(
            ExampleUsers.USER,
            ExampleUsers.AUTHOR
        )
    }),
    ADD_PAGE({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            ExampleUsers.AUTHOR,
            ExampleUsers.SUPER_AUTHOR
        )
    }),
    EDIT_PAGE({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            ExampleUsers.USER,
            ExampleUsers.AUTHOR,
            ExampleUsers.SUPER_AUTHOR
        )
    }),
    EDIT_PAGE_PROPERTIES({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            ExampleUsers.USER,
            ExampleUsers.AUTHOR,
            ExampleUsers.SUPER_AUTHOR
        )
    });

    override fun toTestCaseConfiguration(): TestCaseConfiguration {
        val testCaseConfiguration = TestCaseConfiguration().apply(initConfig)
        testCaseConfiguration.allUsers(ExampleUsers.values())
        return testCaseConfiguration
    }
}