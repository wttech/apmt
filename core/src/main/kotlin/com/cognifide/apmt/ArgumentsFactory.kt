package com.cognifide.apmt

import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

fun createArguments(testCase: TestCaseConfiguration): Stream<Arguments> {
    val arguments = mutableListOf<Arguments>()
    pairs(testCase.users, testCase.paths).forEach { arguments.add(Arguments.of(it.first, it.second)) }
    return arguments.stream()
}

fun createInvertedArguments(testCase: TestCaseConfiguration): Stream<Arguments> {
    val arguments = mutableListOf<Arguments>()
    val allPairs = pairs(testCase.allUsers, testCase.paths)
    val pairs = allPairs - pairs(testCase.users, testCase.paths)
    pairs.forEach { arguments.add(Arguments.of(it.first, it.second)) }
    return arguments.stream()
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