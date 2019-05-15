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
            .header("CSRF-Token", obtainCsrfToken(user, instance))
            .auth().basic(user.username, user.password)
    }

    private fun obtainCsrfToken(user: User, instance: Instance): String {
        return RestAssured
            .given()
            .auth().preemptive().basic(user.username, user.password)
            .`when`()
            .get(instance.url + "/libs/granite/csrf/token.json")
            .then()
            .contentType(ContentType.JSON)
            .extract().path("token")
    }
}
