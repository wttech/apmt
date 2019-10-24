package com.cognifide.apmt.common

class PageContent : Resource() {

    var jcrTitle: String? by ResourcePropertyDelegate(properties, "jcr:title")
    var cqTemplate: String? by ResourcePropertyDelegate(properties, "cq:template")

    init {
        jcrPrimaryType = "cq:PageContent"
    }
}