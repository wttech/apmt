package com.cognifide.apmt

import org.junit.jupiter.api.Assumptions
import org.junit.jupiter.params.provider.Arguments


fun createAllowed(testCases: List<TestCaseConfiguration>): List<Arguments> {
    return toArguments(testCases.flatMap { createAllowed(it) }.distinctBy { it.user.username + it.path }.sorted())
}

fun createDenied(testCases: List<TestCaseConfiguration>): List<Arguments> {
    return toArguments(testCases.flatMap { createDenied(it) }.distinctBy { it.user.username + it.path }.sorted())
}

private fun createAllowed(testCase: TestCaseConfiguration): List<UserAndPath> {
    return createArguments(
        testCase.paths,
        testCase.allowedUsers,
        testCase.deniedUsers,
        testCase.allUsers,
        testCase.allowedPairsPredicate,
        testCase.deniedPairsPredicate
    )
}

private fun createDenied(testCase: TestCaseConfiguration): List<UserAndPath> {
    return createArguments(
        testCase.paths,
        testCase.deniedUsers,
        testCase.allowedUsers,
        testCase.allUsers,
        testCase.deniedPairsPredicate,
        testCase.allowedPairsPredicate
    )
}

private fun createArguments(
    paths: List<String>,
    users: List<User>,
    opposedUsers: List<User>,
    allUsers: List<User>,
    predicate: ((user: User, path: String) -> Boolean)?,
    opposedPredicate: ((user: User, path: String) -> Boolean)?
): List<UserAndPath> {
    return when {
        users.isNotEmpty() -> pairs(users, paths, predicate)
        opposedUsers.isNotEmpty() && allUsers.isNotEmpty() -> {
            val allPairs = pairs(allUsers, paths)
            allPairs - pairs(opposedUsers, paths, opposedPredicate)
        }
        else -> listOf()
    }
}

private fun toArguments(usersAndPaths: Collection<UserAndPath>): List<Arguments> {
    val arguments = mutableListOf<Arguments>()
    usersAndPaths.forEach { arguments.add(Arguments.of(it.user, it.path)) }

    Assumptions.assumeFalse(arguments.isEmpty(), "No arguments")

    return arguments.toList()
}

private fun pairs(
    users: List<User>,
    paths: List<String>,
    predicate: ((user: User, path: String) -> Boolean)?
): List<UserAndPath> {
    val pairs = pairs(users, paths)
    if (predicate != null) {
        return pairs.filter { predicate(it.user, it.path) }
    }
    return pairs
}

private fun pairs(users: List<User>, paths: List<String>): List<UserAndPath> {
    val results = mutableListOf<UserAndPath>()
    for (user in users) {
        for (path in paths) {
            results.add(UserAndPath(user, path))
        }
    }
    return results.toList()
}

data class UserAndPath(val user: User, val path: String) : Comparable<UserAndPath> {

    override fun compareTo(other: UserAndPath): Int {
        return this.user.username.compareTo(other.user.username)
    }
}