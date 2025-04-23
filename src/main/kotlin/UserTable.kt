import org.jetbrains.exposed.sql.Table

object UserTable : Table("public.users") {
    val id = uuid("id")
    val name = text("name")
    val email = text("email")
}
