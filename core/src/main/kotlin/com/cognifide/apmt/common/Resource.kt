package com.cognifide.apmt.common

open class Resource {

    protected val properties: MutableMap<String, String> = mutableMapOf()
    protected val children: MutableMap<String, Resource> = mutableMapOf()

    var jcrPrimaryType: String? by ResourcePropertyDelegate(properties, "jcr:primaryType")
    var slingResourceType: String? by ResourcePropertyDelegate(properties, "sling:resourceType")

    fun properties(vararg pairs: Pair<String, String>) {
        properties.putAll(pairs)
    }

    infix fun String.set(value: String) {
        properties[this] = value
    }

    fun addChild(name: String, child: Resource) {
        children[name] = child
    }

    operator fun String.invoke(child: Resource.() -> Unit) {
        children[this] = Resource().apply(child)
    }

    internal fun toMap(): Map<String, String> {
        val map = properties.toMutableMap()
        children.forEach { (name, child) ->
            val childProperties = child.toMap().mapKeys { entry -> "$name/${entry.key}" }
            map.putAll(childProperties)
        }
        return map.toMap()
    }
}