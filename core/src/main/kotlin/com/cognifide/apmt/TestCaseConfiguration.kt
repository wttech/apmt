package com.cognifide.apmt


class TestCaseConfiguration @JvmOverloads constructor(
    internal var allowedUsers: List<User> = listOf(),
    internal var deniedUsers: List<User> = listOf(),
    internal var paths: List<String> = listOf(),
    internal var allUsers: List<User> = listOf(),
    internal var allowedPairsPredicate: ((user: User, path: String) -> Boolean)? = null,
    internal var deniedPairsPredicate: ((user: User, path: String) -> Boolean)? = null
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

    fun allowedUsers(vararg users: User) {
        this.allowedUsers = users.toList()
    }

    fun deniedUsers(vararg users: User) {
        this.deniedUsers = users.toList()
    }

    fun allowedPairsPredicate(predicate: (user: User, path: String) -> Boolean) {
        this.allowedPairsPredicate = predicate
    }

    fun deniedPairsPredicate(predicate: (user: User, path: String) -> Boolean) {
        this.deniedPairsPredicate = predicate
    }
}
