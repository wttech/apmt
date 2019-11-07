package com.cognifide.apmt.common

import kotlin.reflect.KProperty

class ResourcePropertyDelegate(
    private val properties: MutableMap<String, String>,
    private val name: String
) {

    operator fun getValue(thisRef: Any?, property: KProperty<*>): String? {
        return properties[name]
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String?) {
        if (value != null) {
            properties[name] = value
        } else {
            properties.remove(name)
        }
    }
}