package com.cognifide.apmt

import org.junit.jupiter.api.Assertions

object Checks {

    fun pathContainsUser(path: String, user: String) {
        Assertions.assertTrue(path.contains(user))
    }

    fun alwaysSuccess(path: String, user: String) {
        Assertions.assertTrue(true)
    }
}
