package com.cognifide.apmt.tests

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.TestCaseConfiguration

enum class ApmtTestCases(private val initConfig: TestCaseConfiguration.() -> Unit) : TestCase {

    ADD_ASSET({
        paths(
            "/content/dam/my-product/screens",
            "/content/dam/my-product/images"
        )
        allowedUsers(
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    }),
    EDIT_ASSET({
        paths(
            "/content/dam/my-product/screens",
            "/content/dam/my-product/images"
        )
        deniedUsers(
            ApmtUsers.USER,
            ApmtUsers.AUTHOR
        )
    }),
    REMOVE_ASSET({
        paths(
            "/content/dam/my-product/screens",
            "/content/dam/my-product/images"
        )
        deniedUsers(
            ApmtUsers.USER,
            ApmtUsers.AUTHOR
        )
    }),
    ADD_PAGE({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    }),
    EDIT_PAGE({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            ApmtUsers.USER,
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    }),
    EDIT_PAGE_PROPERTIES({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            ApmtUsers.USER,
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    }),
    OPEN_PAGE({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            *ApmtUsers.values()
        )
    }), ;

    override fun toTestCaseConfiguration(): TestCaseConfiguration {
        val testCaseConfiguration = TestCaseConfiguration().apply(initConfig)
        testCaseConfiguration.allUsers(ApmtUsers.values())
        return testCaseConfiguration
    }
}