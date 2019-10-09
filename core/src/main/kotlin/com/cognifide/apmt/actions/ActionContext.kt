package com.cognifide.apmt.actions

import com.cognifide.apmt.User
import com.cognifide.apmt.config.Instance
import io.restassured.RestAssured
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification

object ActionContext {

    fun basicRequestSpec(user: User, instance: Instance): RequestSpecification {
        return RestAssured
            .given()
            .headers(instance.headers)
            .header(CSRF_TOKEN, obtainCsrfToken(user, instance))
            .auth().preemptive().basic(user.username, user.password)
    }

    private fun obtainCsrfToken(user: User, instance: Instance): String {
        return RestAssured
            .given()
            .headers(instance.headers)
            .auth().preemptive().basic(user.username, user.password)
            .`when`()
            .get(instance.url + CSRF_ENDPOINT)
            .then()
            .contentType(ContentType.JSON)
            .extract().path("token")
    }
}
