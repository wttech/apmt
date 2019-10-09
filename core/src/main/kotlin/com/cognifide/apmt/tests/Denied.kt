package com.cognifide.apmt.tests

import com.cognifide.apmt.tests.ApmtBaseTest.Companion.DENIED
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ParameterizedTest
@MethodSource(DENIED)
annotation class Denied