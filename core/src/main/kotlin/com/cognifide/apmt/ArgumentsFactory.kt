package com.cognifide.apmt

import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.params.provider.Arguments

typealias UserAndPath = Pair<User, String>

fun createArguments(testCases: List<TestCaseConfiguration>): List<Arguments> {
    return toArguments(testCases.flatMap { createArguments(it) }.toSet())
}

fun createArguments(testCase: TestCaseConfiguration): List<UserAndPath> {
    return pairs(testCase.users, testCase.paths, testCase.predicate)
}

fun createInvertedArguments(testCases: List<TestCaseConfiguration>): List<Arguments> {
    return toArguments(testCases.flatMap { createInvertedArguments(it) }.toSet())
}

fun createInvertedArguments(testCase: TestCaseConfiguration): List<UserAndPath> {
    val allPairs = pairs(testCase.allUsers, testCase.paths)
    return allPairs - pairs(testCase.users, testCase.paths, testCase.predicate)
}

private fun toArguments(usersAndPaths: Collection<UserAndPath>): List<Arguments> {
    val arguments = mutableListOf<Arguments>()
    usersAndPaths.forEach { arguments.add(Arguments.of(it.first, it.second)) }

    Assumptions.assumeFalse(arguments.isEmpty(), "No arguments")

    return arguments.toList()
}

private fun pairs(
    users: List<User>,
    paths: List<String>,
    predicate: (user: User, path: String) -> Boolean
) = pairs(users, paths)
    .filter { predicate(it.first, it.second) }

private fun pairs(users: List<User>, paths: List<String>): List<UserAndPath> {
    val results = mutableListOf<UserAndPath>()
    for (user in users) {
        for (path in paths) {
            results.add(UserAndPath(user, path))
        }
    }
    return results.toList()
}