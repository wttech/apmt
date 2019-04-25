import org.junit.jupiter.api.DynamicTest

fun createPermissionTests(initConfiguration: PermissionTestsConfiguration.() -> Unit): List<DynamicTest> {
    val configuration = PermissionTestsConfiguration().apply(initConfiguration)
    val dynamicTests = mutableListOf<DynamicTest>()
    configuration.testCaseConfigurations.forEach { testCase ->
        for (user in testCase.users) {
            for (path in testCase.paths) {
                dynamicTests.add(DynamicTest.dynamicTest(testCase.name) {
                    testCase.test?.invoke(path, user.username)
                })
            }
        }
    }
    return dynamicTests
}