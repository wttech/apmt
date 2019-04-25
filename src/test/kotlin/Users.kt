enum class Users(
    override val password: String,
    override val username: String,
    override val groups: List<Group>
) : User {
    GLOBAL_AUTHOR("xxx", "author", listOf(Groups.AUTHOR)),
    PL_AUTHOR("xxx", "pl_author", listOf(Groups.AUTHOR))
}