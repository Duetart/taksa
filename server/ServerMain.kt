package ru.phts.kotlin.taksa.server
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
fun main() {
    println("hello from server!")
    embeddedServer(Netty, port = 8080) {
        install(Authentication) {
            basic("auth-basic") {
                realm = "Access to the '/' path"
                validate { credentials ->
                    if (credentials.name == "mike" && credentials.password == "cenin") {
                        UserIdPrincipal(credentials.name)
                    } else {
                        null
                    }
                }
            }
        }
        routing {
            authenticate("auth-basic") {
                get("/") {
                    call.respondText("Hello, ${call.principal<UserIdPrincipal>()?.name}!")
                }
            }
        }
    }.start(wait = true)

}
