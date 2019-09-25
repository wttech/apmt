package com.cognifide.apmt.actions.asset

import com.cognifide.apmt.User
import com.cognifide.apmt.actions.Action
import com.cognifide.apmt.actions.ActionContext
import com.cognifide.apmt.config.Instance
import io.restassured.response.Response
import org.apache.commons.lang3.StringUtils

class AssetEdition(
    private val instance: Instance,
    private val user: User,
    private val path: String
) : Action {

    private val assetCreationAction: Action

    init {
        val assetPath = fetchActualAssetPath(path)
        assetCreationAction = AssetCreation(instance, user, assetPath)
    }

    private fun fetchActualAssetPath(path: String): String {
        return StringUtils.substringBefore(path, "/jcr:content")
    }

    override fun prepare(): Response? {
        return assetCreationAction.execute()
    }

    override fun execute(): Response {
        return ActionContext.basicRequestSpec(user, instance)
            .formParam("jcr:title", "Some Title")
            .`when`()
            .post(instance.url + path)
    }

    override fun undo(): Response? {
        return assetCreationAction.undo()
    }

    override fun successCode(): Int = 200
    override fun failureCode(): Int = 500
}
