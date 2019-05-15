package com.cognifide.apmt

import com.cognifide.apmt.config.Instance

val TEST_USER = object : User {
    override val username: String = "testuser"
    override val password: String = "testpassword"
    override val groups: List<Group> = emptyList()
}

val MOCK_SERVER = Instance("Mock Server", "http://localhost:8080")
