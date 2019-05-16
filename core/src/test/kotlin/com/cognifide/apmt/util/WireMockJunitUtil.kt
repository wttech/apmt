package com.cognifide.apmt.util

import com.github.tomakehurst.wiremock.WireMockServer
import org.junit.jupiter.api.extension.ExtensionContext

fun getWireMockServer(context: ExtensionContext?): WireMockServer {
    return context?.getStore(APMT_NAMESPACE)
        ?.getOrComputeIfAbsent(WIREMOCK_SERVER, { WireMockServer() }) as WireMockServer
}
