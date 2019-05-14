package com.cognifide.aem.apmt

interface User {

    val username: String
    val password: String
    val groups: List<Group>
}