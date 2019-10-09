package com.cognifide.apmt.common

class Resource(
    var jcrPrimaryType: String? = null,
    var slingResourceType: String? = null,
    private val properties: MutableMap<String, String> = mutableMapOf()
) {

    fun properties(vararg pairs: Pair<String, String>) {
        properties.putAll(pairs)
    }

    infix fun String.set(value: String) {
        properties[this] = value
    }

    internal fun toMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        if (jcrPrimaryType != null) {
            map["jcr:primaryType"] = jcrPrimaryType!!
        }
        if (slingResourceType != null) {
            map["sling:resourceType"] = slingResourceType!!
        }
        map.putAll(properties)
        return map.toMap()
    }
}