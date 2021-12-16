package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.JwtServices
import com.example.hash
import com.example.hashKey
import com.example.module.EPSession
import com.example.module.User
import com.example.repository.DataBaseFactory
import com.example.repository.EmojiPhrasesRepository
import com.example.wepapp.*
import freemarker.cache.ClassTemplateLoader
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.freemarker.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.locations.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import java.net.URI
import java.util.concurrent.TimeUnit

fun Application.configureRouting() {
    install(DefaultHeaders)

    install(ContentNegotiation) {
        gson()
    }
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
    }
    install(Locations)
    data class MySession(val count: Int = 0)
    install(Sessions) {
        cookie<EPSession>("SESSION") {
            transform(SessionTransportTransformerMessageAuthentication(hashKey))
        }
    }
    val hashFunction = { s: String ->
        hash(s)
    }
    DataBaseFactory.init()



    /*  authentication {
    basic(name = "auth") {
        realm = "Ktor Server"
        validate { credentials ->
            if (credentials.password == "${credentials.name}123") {
                UserIdPrincipal(credentials.name)
            } else {
                null
            }
        }

      *//*  form(name = "myauth2") {
                userParamName = "user"
                passwordParamName = "password"
                challenge {
                    *//**//**//**//*
                }
            }*//*
        }
    }*/
    val db = EmojiPhrasesRepository()
    val jwtServices = JwtServices()
    authentication {
        jwt("jwt") {
            realm ="emojiphrases app"
            verifier(
                jwtServices.verifier
            )
            validate {
                val payload=it.payload
                val claim=payload.getClaim("id")
                val claimString=claim.asString()
                val user=db.userById(claimString)
                user
            }
        }
    }    // Starting point for a Ktor app:
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        //api
        postPhrase(db)
        phrasesApi(db, hashFunction)
        removePhrase(db)
        signIn(db, hashFunction)
        signUp(db, hashFunction)
        signOut()
        login(db,jwtServices)
    }

}

const val API_VERSION = "/api/v1"

suspend fun ApplicationCall.redirect(location: Any) {
    respondRedirect(application.locations.href(location))
}

fun ApplicationCall.refererHost() = request.header(HttpHeaders.Referrer)?.let { URI.create(it).host }

fun ApplicationCall.securityCode(date: Long, user: User, hashFunction: (String) -> String) =
    hashFunction("$date:${user.userId}:${request.host()}:${refererHost()}")

fun ApplicationCall.verifyCode(date: Long, user: User, code: String, hashFunction: (String) -> String) =
    securityCode(date, user, hashFunction) == code &&
            (System.currentTimeMillis() - date).let {
                it > 0 && it < TimeUnit.MILLISECONDS.convert(2, TimeUnit.HOURS)
            }

val ApplicationCall.apiUser get()=authentication.principal<User>()