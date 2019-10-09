package com.cognifide.apmt.tests

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.createAllowed
import com.cognifide.apmt.createDenied

abstract class ApmtBaseTest(private vararg val testCases: TestCase) {

    fun allowed() = createAllowed(testCases.map { it.toTestCaseConfiguration() })

    fun denied() = createDenied(testCases.map { it.toTestCaseConfiguration() })

    companion object {
        const val ALLOWED = "allowed"
        const val DENIED = "denied"
    }
}