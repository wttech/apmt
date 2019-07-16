package com.cognifide.apmt

import org.junit.jupiter.api.DynamicContainer
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.params.provider.Arguments
import java.util.stream.Stream

fun createPermissionTests(initConfiguration: PermissionTestsConfiguration.() -> Unit): List<DynamicContainer> {
    val configuration = PermissionTestsConfiguration().apply(initConfiguration)
    val dynamicContainers = mutableListOf<DynamicContainer>()
    configuration.testCaseConfigurations.forEach { testCase ->
        val dynamicTests = mutableListOf<DynamicTest>()
        for (user in testCase.users) {
            for (path in testCase.paths) {
                dynamicTests.add(DynamicTest.dynamicTest("$user on path: $path") {
                    testCase.test?.invoke(path, user.username)
                })
            }
        }
        dynamicContainers.add(DynamicContainer.dynamicContainer(testCase.name, dynamicTests))
    }
    return dynamicContainers
}

fun createArguments(initConfiguration: PermissionTestsConfiguration.() -> Unit): Stream<Arguments> {
    val configuration = PermissionTestsConfiguration().apply(initConfiguration)
    val arguments = mutableListOf<Arguments>()
    configuration.testCaseConfigurations.forEach { testCase ->
        pairs(testCase.paths, testCase.users).forEach { arguments.add(Arguments.of(it.first, it.second)) }
    }
    return arguments.stream()
}

fun createInvertedArguments(initConfiguration: PermissionTestsConfiguration.() -> Unit): Stream<Arguments> {
    val configuration = PermissionTestsConfiguration().apply(initConfiguration)
    val arguments = mutableListOf<Arguments>()
    configuration.testCaseConfigurations.forEach { testCase ->
        val allPairs = pairs(testCase.paths, configuration.users)
        val pairs = allPairs - pairs(testCase.paths, testCase.users)
        pairs.forEach { arguments.add(Arguments.of(it.first, it.second)) }
    }
    return arguments.stream()
}

fun pairs(paths: List<String>, users: List<User>): List<Pair<String, User>> {
    val results = mutableListOf<Pair<String, User>>()
    for (user in users) {
        for (path in paths) {
            results.add(Pair(path, user))
        }
    }
    return results.toList()
}