package com.cognifide.apmt


interface ITestCaseConfiguration {
    var allowedUsers: List<User>
    var deniedUsers: List<User>
    var paths: List<String>
    var allUsers: List<User>
    var allowedPairsPredicate: ((user: User, path: String) -> Boolean)?
    var deniedPairsPredicate: ((user: User, path: String) -> Boolean)?

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

class TestCaseConfiguration @JvmOverloads constructor(
    override var allowedUsers: List<User> = listOf(),
    override var deniedUsers: List<User> = listOf(),
    override var paths: List<String> = listOf(),
    override var allUsers: List<User> = listOf(),
    override var allowedPairsPredicate: ((user: User, path: String) -> Boolean)? = null,
    override var deniedPairsPredicate: ((user: User, path: String) -> Boolean)? = null,
    initConfig: (TestCaseConfiguration.() -> Unit)? = null
) : ITestCaseConfiguration {

    init {
        if (initConfig != null) {
            this.apply(initConfig)
        }
    }
}
