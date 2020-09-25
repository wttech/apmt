package com.cognifide.apmt.config

import com.cognifide.apmt.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule

object ConfigurationProvider {

    private val configuration: Configuration

    val authorInstance: Instance
        get() = configuration.instances.author

    val publishInstance: Instance
        get() = configuration.instances.publish

    val apmtUser: User
        get() = ApmtUser(configuration.apmtUser.username, configuration.apmtUser.password)

    init {
        configuration = fetchFromFile("/apmt.yaml", Configuration::class.java)
    }

    private fun <T> fetchFromFile(fileName: String, clazz: Class<T>): T {
        javaClass.getResourceAsStream(fileName).use {
            return ObjectMapper(YAMLFactory()).registerModule(KotlinModule()).readValue(it, clazz)
        }
    }

    private class ApmtUser(
        override val username: String,
        override val password: String
    ) : User
}
