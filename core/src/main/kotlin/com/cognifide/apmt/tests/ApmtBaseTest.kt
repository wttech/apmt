package com.cognifide.apmt.tests

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.createAllowed
import com.cognifide.apmt.createDenied
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach

abstract class ApmtBaseTest(private vararg val testCases: TestCase) {

    private var undoActions: MutableList<Action> = mutableListOf()

    fun allowed() = createAllowed(testCases.map { it.toTestCaseConfiguration() })

    fun denied() = createDenied(testCases.map { it.toTestCaseConfiguration() })

    fun addUndoAction(action: Action) {
        undoActions.add(action)
    }

    @BeforeEach
    fun beforeEachTest() {
        undoActions.clear()
    }

    @AfterEach
    fun afterEachTest() {
        undoActions.asReversed().forEach { it.undo() }
    }

    companion object {
        const val ALLOWED = "allowed"
        const val DENIED = "denied"
    }
}