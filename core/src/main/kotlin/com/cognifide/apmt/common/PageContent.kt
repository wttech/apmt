package com.cognifide.apmt.common

class PageContent(
    var jcrTitle: String = "[APMT] Test Page",
    var cqTemplate: String = "",
    var slingResourceType: String = "",
    private val properties: MutableMap<String, String> = mutableMapOf()
) {

    fun properties(vararg pairs: Pair<String, String>) {
        properties.putAll(pairs)
    }

    infix fun String.set(value: String) {
        properties[this] = value
    }

    internal fun toMap(): Map<String, String> {
        val pageProperties = mutableMapOf<String, String>()
        pageProperties["jcr:content/jcr:title"] = jcrTitle
        pageProperties["jcr:content/sling:resourceType"] = slingResourceType
        pageProperties["jcr:content/cq:template"] = cqTemplate

        properties.forEach { (key, value) -> pageProperties["jcr:content/$key"] = value }

        return pageProperties.toMap()
    }
}