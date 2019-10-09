package com.cognifide.apmt.actions.page

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.common.PageContent
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class CreatePage(
    private val instance: Instance,
    private val user: User,
    private val path: String,
    pageContent: (PageContent.() -> Unit)? = null
) : Action {

    private val pageParams: MutableMap<String, String> = mutableMapOf()

    init {
        val newPageContent = PageContent()
        if (pageContent != null) {
            newPageContent.apply(pageContent)
        }
        this.pageParams.putAll(DEFAULT_PARAMS)
        this.pageParams.putAll(newPageContent.toMap())
    }

    override fun execute(): Response {
        val url = instance.url + path
        return ActionContext.basicRequestSpec(user, instance)
            .given()
            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
            .formParams(pageParams)
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

    companion object {
        private val DEFAULT_PARAMS = mapOf(
            "jcr:primaryType" to "cq:Page",
            "jcr:content/jcr:primaryType" to "cq:PageContent"
        )
    }
}
