package com.example.wepapp

import com.example.module.EPSession
import com.example.plugins.API_VERSION
import io.ktor.server.application.*
import io.ktor.server.locations.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*

const val SIGN_OUT_ENDPOINT="$API_VERSION/signout"
@Location(SIGN_OUT_ENDPOINT)
class SignOut

fun Route.signOut(){
    get<SignOut> {
       call.sessions.clear<EPSession>()
    }
}