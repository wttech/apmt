package com.cognifide.apmt.actions.asset

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class AssetCreation(
    private val instance: Instance,
    private val user: User,
    private val path: String
) : Action {

    override fun execute(): Response {
        return ActionContext.basicRequestSpec(user, instance)
            .formParam("jcr:primaryType", "sling:OrderedFolder")
            .formParam("jcr:content/jcr:primaryType", "nt:unstructured")
            .formParam("jcr:content/jcr:title", "Test Title")
            .`when`()
            .post(instance.url + path)
    }

    override fun undo(): Response? {
        return ActionContext.basicRequestSpec(user, instance)
            .delete(instance.url + path)
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 404
}
