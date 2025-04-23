import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.contextual
import java.util.*

val SHARD_MANAGER = ShardManager
val REPOSITORY = UserRepository(SHARD_MANAGER)

fun main() {
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                ignoreUnknownKeys = true
                serializersModule = SerializersModule {
                    contextual(UUIDSerializer)
                }
            }
        )
    }

    routing {
        get("/users/{id}") {
            val id = call.parameters["id"]?.let(UUID::fromString)
            if (id != null) {
                val user = REPOSITORY.getUserById(id)
                if (user != null) {
                    call.respond(user)
                } else {
                    call.respondText("User not found", status = HttpStatusCode.NotFound)
                }
            } else {
                call.respondText("Invalid UUID", status = HttpStatusCode.BadRequest)
            }
        }

        post("/users") {
            val request = call.receive<User>()
            val user = request.copy(id = UUID.randomUUID())

            REPOSITORY.insertUser(user)
            call.respondText("User: $user created", status = HttpStatusCode.Created)
        }
    }
}
