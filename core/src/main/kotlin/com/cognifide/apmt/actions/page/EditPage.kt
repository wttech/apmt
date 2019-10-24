package com.cognifide.apmt.actions.page

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.common.PageContent
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class EditPage(
    private val instance: Instance,
    private val user: User,
    private val path: String,
    private val pageContent: (PageContent.() -> Unit) = {}
) : Action {

    override fun execute(): Response {
        val content = PageContent()
        content.apply(pageContent)
        val url = instance.url + path + "/jcr:content"
        return ActionContext.basicRequestSpec(user, instance)
            .given()
            .formParams(content.toMap())
            .`when`()
            .post(url)
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 500

}
