package com.cognifide.apmt.tests

import com.cognifide.apmt.tests.ApmtBaseTest.Companion.ALLOWED
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@ParameterizedTest(name = "{index} => User: {0} Path: {1}")
@MethodSource(ALLOWED)
annotation class Allowed