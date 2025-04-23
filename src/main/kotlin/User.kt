import kotlinx.serialization.Serializable
import kotlinx.serialization.Contextual
import java.util.UUID

@Serializable
data class User(
    @Contextual val id: UUID? = null,
    val name: String,
    val email: String
)
