package com.cognifide.apmt.common

class Page : Resource() {

    init {
        jcrPrimaryType = "cq:Page"
    }

    fun jcrContent(pageContent: PageContent.() -> Unit) {
        addChild("jcr:content", PageContent().apply(pageContent))
    }
}