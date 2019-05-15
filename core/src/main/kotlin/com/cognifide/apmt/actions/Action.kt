package com.cognifide.apmt.actions

import io.restassured.response.Response

interface Action {
    fun prepare(): Response?
    fun execute(): Response?
    fun undo(): Response?

    fun successCode(): Int
    fun failureCode(): Int
}
