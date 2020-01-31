package com.cognifide.apmt.tests

import com.cognifide.apmt.TestCase
import com.cognifide.apmt.User

enum class ApmtTestCases(initConfig: TestCase.() -> Unit) : TestCase {

    ADD_ASSET({
        paths(
            "/content/dam/my-product/screens",
            "/content/dam/my-product/images"
        )
        allowedUsers(
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    }),
    EDIT_ASSET({
        paths(
            "/content/dam/my-product/screens",
            "/content/dam/my-product/images"
        )
        deniedUsers(
            ApmtUsers.USER,
            ApmtUsers.AUTHOR
        )
    }),
    REMOVE_ASSET({
        paths(
            "/content/dam/my-product/screens",
            "/content/dam/my-product/images"
        )
        deniedUsers(
            ApmtUsers.USER,
            ApmtUsers.AUTHOR
        )
    }),
    ADD_PAGE({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    }),
    EDIT_PAGE({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            ApmtUsers.USER,
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    }),
    EDIT_PAGE_PROPERTIES({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            ApmtUsers.USER,
            ApmtUsers.AUTHOR,
            ApmtUsers.SUPER_AUTHOR
        )
    }),
    OPEN_PAGE({
        paths(
            "/content/my-site/en_gl/home"
        )
        allowedUsers(
            *ApmtUsers.values()
        )
    }), ;

    override var allowedUsers: List<User> = listOf()
    override var deniedUsers: List<User> = listOf()
    override var paths: List<String> = listOf()
    override var allUsers: List<User> = listOf()
    override var allowedPairsPredicate: ((user: User, path: String) -> Boolean)? = null
    override var deniedPairsPredicate: ((user: User, path: String) -> Boolean)? = null

    init {
        this.apply(initConfig)
        this.allUsers(ApmtUsers.values())
    }
}