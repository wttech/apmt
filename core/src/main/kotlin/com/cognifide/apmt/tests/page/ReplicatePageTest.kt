package com.cognifide.apmt.tests.page

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User
import com.cognifide.apmt.actions.page.CreatePage
import com.cognifide.apmt.actions.page.ReplicatePage
import com.cognifide.apmt.config.ConfigurationProvider
import com.cognifide.apmt.tests.Allowed
import com.cognifide.apmt.tests.ApmtBaseTest
import com.cognifide.apmt.tests.Denied
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("Check user permissions to replicate pages")
abstract class ReplicatePageTest(vararg testCases: TestCase) : ApmtBaseTest(*testCases) {

    private var authorInstance = ConfigurationProvider.authorInstance

    @DisplayName("User can replicate pages")
    @ParameterizedTest
    @Allowed
    fun userCanReplicatePage(user: User, path: String) {
        val createPageAction = CreatePage(authorInstance, ConfigurationProvider.adminUser, path)
        createPageAction.execute()
        addUndoAction(createPageAction)

        val pageReplicationAction = ReplicatePage(authorInstance, user, path)
        pageReplicationAction
            .execute()
            .then()
            .assertThat()
            .statusCode(200)

        pageReplicationAction
            .undo()
            .then()
            .assertThat()
            .statusCode(200)
    }

    @DisplayName("User cannot replicate pages")
    @ParameterizedTest
    @Denied
    fun userCannotReplicatePage(user: User, path: String) {
        val createPageAction = CreatePage(authorInstance, ConfigurationProvider.adminUser, path)
        createPageAction.execute()
        addUndoAction(createPageAction)

        val pageReplicationAction = ReplicatePage(authorInstance, user, path)
        pageReplicationAction
            .execute()
            .then()
            .assertThat().statusCode(403)

        val adminPageReplicationAction = ReplicatePage(authorInstance, ConfigurationProvider.adminUser, path)
        adminPageReplicationAction.execute()
        addUndoAction(adminPageReplicationAction)

        pageReplicationAction
            .execute()
            .then()
            .assertThat().statusCode(403)
    }
}
