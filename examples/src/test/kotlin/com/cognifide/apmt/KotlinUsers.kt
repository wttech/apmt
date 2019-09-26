package com.cognifide.apmt

enum class KotlinUsers(
    override val password: String,
    override val username: String
) : User {
    AUTHOR("admin", "admin"),
    SUPER_AUTHOR("admin", "admin")
}
