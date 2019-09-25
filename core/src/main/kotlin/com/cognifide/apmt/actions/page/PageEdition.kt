package com.cognifide.apmt.actions.page

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class PageEdition(
    private val instance: Instance,
    private val user: User,
    private val path: String,
    pageParams: Map<String, String> = mapOf()
) : Action {

    private val pageParams: MutableMap<String, String> = mutableMapOf()

    init {
        this.pageParams.putAll(DEFAULT_PARAMS)
        this.pageParams.putAll(pageParams)
    }

    override fun execute(): Response {
        val url = instance.url + path
        return ActionContext.basicRequestSpec(user, instance)
            .given()
            .formParams(pageParams)
            .`when`()
            .post(url)
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 500

    companion object {
        private val DEFAULT_PARAMS = mapOf("copy-block/text" to "<p>Some text</p>")
    }
}
