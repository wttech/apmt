package com.cognifide.apmt.actions.resource

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.common.Resource
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class EditResource(
    private val instance: Instance,
    private val user: User,
    private val path: String,
    private val resource: (Resource.() -> Unit)
) : Action {

    override fun execute(): Response {
        val newResource = Resource()
        if (resource != null) {
            newResource.apply(resource)
        }
        return ActionContext.basicRequestSpec(user, instance)
            .formParams(newResource.toMap())
            .`when`()
            .post(instance.url + path)
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 500
}
