package com.example.wepapp

import com.example.JwtServices
import com.example.hash
import com.example.plugins.API_VERSION
import com.example.plugins.redirect
import com.example.repository.Repository
import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.locations.post
import io.ktor.server.locations.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

const val LOGIN_ENDPOINT="$API_VERSION/login"

@Location(LOGIN_ENDPOINT)
class Login

fun Route.login(db:Repository,jwtServices: JwtServices){
    post<Login> {
        val params=call.receive<Parameters>()
        val userId=params["userId"]?:return@post call.redirect(it)
        val password=params["password"]?:return@post call.redirect(it)
        val user=db.user(userId, hash = hash(password))
        if (user!=null){
            val token=jwtServices.generateToken(user)
            call.respond(token)

        }else{
            call.respond("Invalid user ")
        }
    }
}