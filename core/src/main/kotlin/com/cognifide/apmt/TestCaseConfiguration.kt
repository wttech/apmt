package com.cognifide.apmt


class TestCaseConfiguration @JvmOverloads constructor(
    internal var users: List<User> = listOf(),
    internal var paths: List<String> = listOf(),
    internal var allUsers: List<User> = listOf(),
    internal var predicate: (user: User, path: String) -> Boolean = { _, _ -> true }
) {

    fun allUsers(allUsers: Array<out User>) {
        this.allUsers = allUsers.toList()
    }

    fun allUsers(allUsers: List<User>) {
        this.allUsers = allUsers
    }

    fun paths(vararg paths: String) {
        this.paths = paths.toList()
    }

    fun users(vararg users: User) {
        this.users = users.toList()
    }
}
