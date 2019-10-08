package com.cognifide.apmt.actions.page

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.config.Instance
import io.restassured.RestAssured
import io.restassured.response.Response

class OpenPage(
    private val instance: Instance,
    private val user: User? = null,
    private val path: String,
    private val queryParams: Map<String, String> = mapOf()
) : Action {

    override fun execute(): Response {
        val url = instance.url + path
        val specification = RestAssured.given()
            .relaxedHTTPSValidation()
        if (user != null) {
            specification.spec(ActionContext.basicRequestSpec(user, instance))
        }
        return specification
            .params(queryParams)
            .`when`()
            .get(url)
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 404
}
