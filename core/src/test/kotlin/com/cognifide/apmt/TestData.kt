package com.cognifide.apmt

import com.cognifide.apmt.config.Instance

val TEST_USER = object : User {
    override val username: String = "testuser"
    override val password: String = "testpassword"
    override val groups: List<Group> = emptyList()
}

val TEST_ADMIN = object : User {
    override val username: String = "testadmin"
    override val password: String = "testpassword"
    override val groups: List<Group> = listOf(object : Group {
        override val groupName: String = "administrators"
    })
}

val MOCK_SERVER = Instance("Mock Server", "http://localhost:8080", TEST_ADMIN)
