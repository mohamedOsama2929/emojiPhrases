package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT").toInt()) {

        install(StatusPages) {
            exception<Throwable> { e ->
                call.respondText(e.localizedMessage,
                ContentType.Text.Plain,HttpStatusCode.InternalServerError)
            }
        }
        configureRouting()
    }.start(wait = false)


}
