package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.http.*
import io.ktor.server.response.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {

        install(StatusPages) {
            exception<Throwable> { e ->
                call.respondText(e.localizedMessage,
                ContentType.Text.Plain,HttpStatusCode.InternalServerError)
            }
        }
        configureRouting()
    }.start(wait = true)


}
