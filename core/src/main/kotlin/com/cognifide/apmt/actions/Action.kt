package com.cognifide.apmt.actions

import com.cognifide.apmt.User
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

interface Action {
    val instance: Instance
    val user: User
    val path: String

    fun prepare(): Response?
    fun execute(): Response?
    fun undo(): Response?

    fun successCode(): Int
    fun failureCode(): Int
}
