package com.cognifide.apmt.actions.tags

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class CreateTag(override val instance: Instance, override val user: User, override val path: String) : Action {

    override fun prepare(): Response? = null

    override fun execute(): Response? {
        return ActionContext.basicRequestSpec(user, instance)
            .formParam("jcr:primaryType", "cq:Tag")
            .formParam("jcr:title", "[APMT] Test Tag")
            .formParam("sling:resourceType", "cq/tagging/components/tag")
            .`when`()
            .post(instance.url + path)
    }

    override fun undo(): Response? {
        return ActionContext.basicRequestSpec(user, instance)
            .`when`()
            .delete(instance.url + path)
    }

    override fun successCode(): Int = 201

    override fun failureCode(): Int = 500
}
