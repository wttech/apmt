package com.cognifide.apmt.actions.page

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class EditPageProperties(
    private val instance: Instance,
    private val user: User,
    private val path: String
) : Action {

    override fun execute(): Response {
        val url = instance.url + path
        return ActionContext.basicRequestSpec(user, instance)
            .given()
            .formParam("jcr:title", "Updated Title")
            .`when`()
            .post(url)
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 500
}
