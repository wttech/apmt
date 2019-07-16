package com.cognifide.apmt.actions

import io.restassured.response.Response

interface Action {
    fun prepare(): Response? = null
    fun execute(): Response?
    fun undo(): Response? = null

    fun successCode(): Int
    fun failureCode(): Int
}
