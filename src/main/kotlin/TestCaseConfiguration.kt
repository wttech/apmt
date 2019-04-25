import kotlin.reflect.KFunction2

class TestCaseConfiguration(
    var name: String = "",
    var test: KFunction2<String, String, Unit>? = null,
    internal val paths: MutableList<String> = mutableListOf(),
    internal val users: MutableList<User> = mutableListOf()
) {

    fun addPath(path: String) {
        paths.add(path)
    }

    fun addUser(user: User) {
        users.add(user)
    }
}