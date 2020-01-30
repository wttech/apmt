package com.cognifide.apmt.tests

import com.cognifide.apmt.BasicTestCase
import com.cognifide.apmt.TestCase

const val APMT_TEXT = "apmt/components/text"
const val APMT_PAGE = "apmt/components/page"
const val APMT_PAGE_TEMPLATE = "apmt/templates/page"

fun testCase(initConfig: TestCase.() -> Unit): TestCase {
    val testCaseConfiguration = BasicTestCase().apply(initConfig)
    testCaseConfiguration.allUsers(ApmtUsers.values())
    return testCaseConfiguration
}