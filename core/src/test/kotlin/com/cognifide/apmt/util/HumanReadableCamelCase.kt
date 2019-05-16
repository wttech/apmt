package com.cognifide.apmt.util

import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.DisplayNameGenerator
import java.lang.reflect.Method

class HumanReadableCamelCase : DisplayNameGenerator.Standard() {
    override fun generateDisplayNameForNestedClass(nestedClass: Class<*>?): String {
        return replaceCamelCase(super.generateDisplayNameForNestedClass(nestedClass))
    }

    override fun generateDisplayNameForMethod(testClass: Class<*>?, testMethod: Method?): String {
        return replaceCamelCase(testMethod?.name).toLowerCase()
    }

    override fun generateDisplayNameForClass(testClass: Class<*>?): String {
        return replaceCamelCase(super.generateDisplayNameForClass(testClass))
    }

    private fun replaceCamelCase(text: String?): String {
        return StringUtils.splitByCharacterTypeCamelCase(text).joinToString(" ")
    }
}
