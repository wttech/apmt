@file:JvmName("Constants")

package com.cognifide.apmt.util

import org.junit.jupiter.api.extension.ExtensionContext

const val WIREMOCK_SERVER: String = "wiremockServer"
val APMT_NAMESPACE: ExtensionContext.Namespace = ExtensionContext.Namespace.create("com.cognifide.apmt")
