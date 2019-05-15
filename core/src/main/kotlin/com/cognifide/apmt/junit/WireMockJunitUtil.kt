package com.cognifide.apmt.junit

import com.github.tomakehurst.wiremock.WireMockServer
import org.junit.jupiter.api.extension.ExtensionContext

fun getWireMockServer(context: ExtensionContext?): WireMockServer {
    return context?.getStore(Constants.APMT_NAMESPACE)
        ?.getOrComputeIfAbsent(Constants.WIREMOCK_SERVER, { WireMockServer() }) as WireMockServer
}
