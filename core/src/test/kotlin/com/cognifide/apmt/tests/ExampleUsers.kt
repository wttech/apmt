package com.cognifide.apmt.tests

import com.cognifide.apmt.User

enum class ExampleUsers(
    override val username: String,
    override val password: String
) : User {
    USER("user", "password"),
    AUTHOR("author", "password"),
    SUPER_AUTHOR("super-author", "password")
}
