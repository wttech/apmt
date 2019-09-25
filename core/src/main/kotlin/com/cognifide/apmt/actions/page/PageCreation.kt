package com.cognifide.apmt.actions.page

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response

class PageCreation(
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
            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
            .formParams(pageParams)
            .`when`()
            .post(url)
    }

    override fun undo(): Response {
        val url = instance.url + path
        return return ActionContext.basicRequestSpec(user, instance)
            .given()
            .`when`()
            .delete(url)
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 500

    companion object {
        private val DEFAULT_PARAMS = mapOf(
            "jcr:primaryType" to "cq:Page",
            "jcr:content/jcr:primaryType" to "cq:PageContent",
            "jcr:content/jcr:title" to "[APT] Test Page",
            "jcr:content/sling:resourceType" to "ey-dff/zgExtension/renderers/eyPageRenderer",
            "jcr:content/cq:template" to "/apps/zg/zenGarden/template/site/zenPage",
            "jcr:content/template" to "/content/ey-sites/ey-com-demo/en_gl/templates/article-template",
            "jcr:content/copy-block/text" to "Test Text"
        )
    }
}
