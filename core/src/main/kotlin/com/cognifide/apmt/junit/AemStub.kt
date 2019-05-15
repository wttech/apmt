package com.cognifide.apmt.junit

import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extensions

@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Extensions(
    ExtendWith(AemStubExtension::class),
    ExtendWith(WireMockExtension::class)
)
annotation class AemStub
