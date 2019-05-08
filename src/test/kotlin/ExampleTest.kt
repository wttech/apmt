import com.cognifide.aem.apmt.PermissionTest

class ExampleTest : PermissionTest({

    "add assets" {
        test = JavaTest::pathContainsUser
        addPath("/content/sites/author")
        addUser(Users.GLOBAL_AUTHOR)
        addUser(Users.PL_AUTHOR)
    }

    "modify assets" {
        test = JavaTest::alwaysSuccess
        addPath("/content/sites/site")
        addUser(Users.GLOBAL_AUTHOR)
    }
})