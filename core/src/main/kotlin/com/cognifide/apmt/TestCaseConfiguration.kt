package com.cognifide.apmt

class TestCaseConfiguration(
    val allUsers: MutableList<User> = mutableListOf(),
    internal val users: MutableList<User> = mutableListOf(),
    internal val paths: MutableList<String> = mutableListOf()
) {

    fun allUsers(users: Array<out User>) = this.allUsers.addAll(users)
    fun allUsers(users: List<User>) = this.allUsers.addAll(users)

    fun paths(vararg paths: String) {
        for (path in paths) {
            this.paths.add(path)
        }
    }

    fun users(vararg users: User) {
        for (user in users) {
            this.users.add(user)
        }
    }
}
