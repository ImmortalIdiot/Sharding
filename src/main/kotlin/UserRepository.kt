import mu.KotlinLogging
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

private const val POSTGRESQL_DRIVER = "org.postgresql.Driver"
private val logger = KotlinLogging.logger {}

class UserRepository(private val shardManager: ShardManager) {
    private fun connect(shardIndex: Int): Database {
        val url = shardManager.getConnectionString(shardIndex).substringBefore("?")
        val user = "postgres"
        val password = "postgres"
        return Database.connect(url, driver = POSTGRESQL_DRIVER, user = user, password = password)
    }

    fun insertUser(user: User) {
        val shardIndex = shardManager.getShardIndex(user.id!!)
        logger.info { "Write user ${user.id} to shard $shardIndex" }
        val db = connect(shardIndex)

        return transaction(db) {
            UserTable.insert {
                it[id] = user.id
                it[name] = user.name
                it[email] = user.email
            }
        }
    }

    fun getUserById(id: UUID): User? {
        val shardIndex = shardManager.getShardIndex(id)
        logger.debug { "Read user $id from shard $shardIndex" }
        val db = connect(shardIndex)

        return transaction(db) {
            val expression = UserTable.id eq id
            val result = UserTable.selectAll().where(expression).singleOrNull()

            if (result != null) {
                logger.info { "Found user: $result" }
                User(
                    id = result[UserTable.id],
                    name = result[UserTable.name],
                    email = result[UserTable.email]
                )

            } else {
                logger.warn { "User with ID $id not found" }
                null
            }
        }
    }
}
