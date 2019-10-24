package com.cognifide.apmt.actions.page

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.common.Page
import com.cognifide.apmt.common.PageContent
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class CreatePage(
    private val instance: Instance,
    private val user: User,
    private val path: String,
    pageContent: (PageContent.() -> Unit) = {}
) : Action {

    private val page = Page()

    init {
        page.jcrContent(pageContent)
    }

    override fun execute(): Response {
        val url = instance.url + path
        return ActionContext.basicRequestSpec(user, instance)
            .given()
            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
            .formParams(page.toMap())
            .`when`()
            .post(url)
    }

    override fun undo(): Response {
        val url = instance.url + path
        return ActionContext.basicRequestSpec(user, instance)
            .given()
            .`when`()
            .delete(url)
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 500
}
