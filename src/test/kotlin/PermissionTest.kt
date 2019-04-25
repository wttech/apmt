import org.junit.jupiter.api.TestFactory

class PermissionTest {

    @TestFactory
    fun createDynamicTests() = createPermissionTests {
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
        testCase("delete assets") {
            test = JavaTest::alwaysSuccess
            addPath("/content/sites/site")
            addUser(Users.GLOBAL_AUTHOR)
        }
    }
}