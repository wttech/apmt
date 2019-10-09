package com.cognifide.apmt.actions.resource

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class RemoveResource(
    private val instance: Instance,
    private val user: User,
    private val path: String
) : Action {

    override fun execute(): Response {
        return ActionContext.basicRequestSpec(user, instance)
            .`when`()
            .delete(instance.url + path)
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 500
}
