import java.util.UUID
import kotlin.math.abs
import kotlin.math.absoluteValue

private object JDBCUrl {
    const val FIRST_SHARD_URL = "jdbc:postgresql://localhost:5433/shard1?user=postgres&password=postgres"
    const val SECOND_SHARD_URL = "jdbc:postgresql://localhost:5434/shard2?user=postgres&password=postgres"
    const val THIRD_SHARD_URL = "jdbc:postgresql://localhost:5435/shard3?user=postgres&password=postgres"
}

object ShardManager {
    private const val SHARD_COUNT = 3

    fun getShardIndex(id: UUID): Int {
        return abs(id.hashCode().absoluteValue % SHARD_COUNT) + 1
    }

    fun getConnectionString(shardIndex: Int): String {
        return when (shardIndex) {
            1 -> JDBCUrl.FIRST_SHARD_URL
            2 -> JDBCUrl.SECOND_SHARD_URL
            3 -> JDBCUrl.THIRD_SHARD_URL
            else -> throw IllegalArgumentException("Invalid shard index")
        }
     }
}
