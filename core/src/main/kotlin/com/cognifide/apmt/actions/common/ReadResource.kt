package com.cognifide.apmt.actions.common

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class ReadResource(override val instance: Instance, override val user: User, override val path: String) : Action {
    override fun prepare(): Response? = null

    override fun execute(): Response? {
        return ActionContext.basicRequestSpec(user, instance)
            .`when`()
            .get(instance.url + path + ".json")
    }

    override fun undo(): Response? = null

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 404
}
