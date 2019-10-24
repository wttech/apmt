package com.cognifide.apmt.common

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class ResourceTest {

    @Test
    fun resourceGeneratesProperMap() {
        //given:
        val resource = createResource {
            slingResourceType = "apmt/resourceType"
            "property" set "value"

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

        //when:
        val map = resource.toMap()

        //then:
        Assertions.assertEquals(
            mapOf(
                "sling:resourceType" to "apmt/resourceType",
                "property" to "value",
                "child1/jcr:primaryType" to "apmt:node",
                "child1/sling:resourceType" to "apmt/resourceType",
                "child2/sling:resourceType" to "apmt/resourceType",
                "child2/grandChild/property1" to "value",
                "child2/grandChild/property2" to "value"
            ), map
        )
    }

    private fun createResource(init: Resource.() -> Unit): Resource {
        return Resource().apply(init)
    }
}