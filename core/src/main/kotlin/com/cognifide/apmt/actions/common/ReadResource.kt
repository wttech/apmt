package com.cognifide.apmt.actions.common

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class ReadResource(
    private val instance: Instance,
    private val user: User,
    private val path: String
) : Action {
    override fun execute(): Response? {
        return ActionContext.basicRequestSpec(user, instance)
            .`when`()
            .get(instance.url + path + ".json")
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 404
}
