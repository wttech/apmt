package com.cognifide.apmt.junit

import org.junit.jupiter.api.extension.ExtensionContext

object Constants {
    const val WIREMOCK_SERVER: String = "wiremockServer"
    val APMT_NAMESPACE: ExtensionContext.Namespace = ExtensionContext.Namespace.create("com.cognifide.apmt")
}
