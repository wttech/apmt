class PermissionTest2 : AbstractPermissionTest({
    testCase("add assets") {
        test = JavaTest::pathContainsUser
        addPath("/content/sites/author")
        addUser(Users.GLOBAL_AUTHOR)
        addUser(Users.PL_AUTHOR)
    }
    testCase("modify assets") {
        test = JavaTest::alwaysSuccess
        addPath("/content/sites/site")
        addUser(Users.GLOBAL_AUTHOR)
    }
})