package com.cognifide.apmt.actions.tags

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class EditTag(override val instance: Instance, override val user: User, override val path: String) : Action {
    override fun prepare(): Response? {
        return CreateTag(instance, instance.adminUser, path).execute()
    }

    override fun execute(): Response? {
        return ActionContext.basicRequestSpec(user, instance)
            .formParam("jcr:title", "[APMT] Changed title")
            .`when`()
            .post(instance.url + path)
    }

    override fun undo(): Response? {
        return CreateTag(instance, instance.adminUser, path).undo()
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 500
}
