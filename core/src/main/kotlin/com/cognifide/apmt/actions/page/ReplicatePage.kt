package com.cognifide.apmt.actions.page

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class ReplicatePage(
    private val instance: Instance,
    private val user: User,
    private val path: String
) : Action {

    override fun execute(): Response {
        val url = instance.url + "/bin/replicate.json"
        return ActionContext.basicRequestSpec(user, instance)
            .given()
            .formParam("cmd", "activate")
            .formParam("path", path)
            .`when`()
            .post(url)
    }

    override fun undo(): Response {
        val url = instance.url + "/bin/replicate.json"
        return ActionContext.basicRequestSpec(user, instance)
            .given()
            .formParam("cmd", "deactivate")
            .formParam("path", path)
            .`when`()
            .post(url)
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 403
}
