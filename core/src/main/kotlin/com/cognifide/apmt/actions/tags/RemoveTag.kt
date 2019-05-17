package com.cognifide.apmt.actions.tags

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response


class RemoveTag(override val instance: Instance, override val user: User, override val path: String) : Action {
    override fun prepare(): Response? {
        return CreateTag(instance, instance.adminUser, path).execute()
    }

    override fun execute(): Response? {
        return CreateTag(instance, user, path).undo()
    }

    override fun undo(): Response? = null

    override fun successCode(): Int = 204

    override fun failureCode(): Int = 500
}
