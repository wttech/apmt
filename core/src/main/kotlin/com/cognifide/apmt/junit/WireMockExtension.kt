package com.cognifide.apmt.junit

import com.github.tomakehurst.wiremock.WireMockServer
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

class WireMockExtension : ParameterResolver {
    override fun resolveParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Any {
        return getWireMockServer(extensionContext)
    }

    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean =
        parameterContext?.parameter?.type == WireMockServer::class.java
}
