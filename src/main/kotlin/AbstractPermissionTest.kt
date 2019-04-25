import org.junit.jupiter.api.TestFactory

abstract class AbstractPermissionTest(
    private val configuration: PermissionTestsConfiguration.() -> Unit
) {
    @TestFactory
    fun runTests() = createPermissionTests(configuration)
}