package com.cognifide.apmt.junit

import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

class AemStubExtension : BeforeEachCallback, AfterEachCallback {
    override fun beforeEach(context: ExtensionContext?) {
        getWireMockServer(context).start()
    }

    override fun afterEach(context: ExtensionContext?) {
        getWireMockServer(context).stop()
    }
}
