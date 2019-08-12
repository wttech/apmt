package com.cognifide.apmt

import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.params.provider.Arguments

fun createArguments(testCase: TestCaseConfiguration): List<Arguments> {
    val arguments = mutableListOf<Arguments>()
    pairs(testCase.users, testCase.paths).forEach { arguments.add(Arguments.of(it.first, it.second)) }

    Assumptions.assumeFalse(arguments.isEmpty(), "No arguments")

    return arguments.toList()
}

fun createInvertedArguments(testCase: TestCaseConfiguration): List<Arguments> {
    val arguments = mutableListOf<Arguments>()
    val allPairs = pairs(testCase.allUsers, testCase.paths)
    val pairs = allPairs - pairs(testCase.users, testCase.paths)
    pairs.forEach { arguments.add(Arguments.of(it.first, it.second)) }

    Assumptions.assumeFalse(arguments.isEmpty(), "No arguments")

    return arguments.toList()
}

fun pairs(users: List<User>, paths: List<String>): List<Pair<User, String>> {
    val results = mutableListOf<Pair<User, String>>()
    for (user in users) {
        for (path in paths) {
            results.add(Pair(user, path))
        }
    }
    return results.toList()
}