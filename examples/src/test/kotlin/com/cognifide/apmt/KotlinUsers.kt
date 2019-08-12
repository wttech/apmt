package com.cognifide.apmt

enum class KotlinUsers(
    override val password: String,
    override val username: String,
    override val groups: List<Group>
) : User {
    AUTHOR("admin", "admin", listOf(KotlinGroups.AUTHOR)),
    SUPER_AUTHOR("admin", "admin", listOf(KotlinGroups.SUPER_AUTHOR))
}
