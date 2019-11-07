package com.cognifide.apmt.common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class PageTest {

    @Test
    fun pageGeneratesProperMap() {
        //given:
        val page = createResource {
            jcrContent {
                jcrTitle = "[APMT] Test Page"

                "child1" {
                    jcrPrimaryType = "apmt:node"
                    slingResourceType = "apmt/resourceType"
                }

                "child2" {
                    slingResourceType = "apmt/resourceType"
                    "grandChild" {
                        "property1" set "value"
                        "property2" set "value"
                    }
                }
            }
        }

        //when:
        val map = page.toMap()

        //then:
        Assertions.assertEquals(
            mapOf(
                "jcr:primaryType" to "cq:Page",
                "jcr:content/jcr:primaryType" to "cq:PageContent",
                "jcr:content/jcr:title" to "[APMT] Test Page",
                "jcr:content/child1/jcr:primaryType" to "apmt:node",
                "jcr:content/child1/sling:resourceType" to "apmt/resourceType",
                "jcr:content/child2/sling:resourceType" to "apmt/resourceType",
                "jcr:content/child2/grandChild/property1" to "value",
                "jcr:content/child2/grandChild/property2" to "value"
            ), map
        )
    }

    private fun createResource(init: Page.() -> Unit): Page {
        return Page().apply(init)
    }
}