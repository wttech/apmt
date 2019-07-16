import com.cognifide.apmt.tests.TestWitCallbacks

class ExampleTest2 : TestWitCallbacks({

    registerUsers(Users.values())

    "add assets" {
        addPath("/content/sites/publish")
        addPath("/content/sites/something")
        addUser(Users.GLOBAL_AUTHOR)
        addUser(Users.PL_AUTHOR)
    }

    "modify assets" {
        addPath("/content/sites/site")
        addUser(Users.GLOBAL_AUTHOR)
    }

})
